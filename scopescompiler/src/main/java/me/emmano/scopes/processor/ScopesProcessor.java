package me.emmano.scopes.processor;

import com.squareup.javawriter.JavaWriter;

import java.io.IOException;
import java.util.EnumSet;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.regex.Pattern;

import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.RoundEnvironment;
import javax.annotation.processing.SupportedAnnotationTypes;
import javax.annotation.processing.SupportedSourceVersion;
import javax.inject.Inject;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.Element;
import javax.lang.model.element.Modifier;
import javax.lang.model.element.TypeElement;
import javax.lang.model.type.MirroredTypesException;
import javax.lang.model.type.TypeMirror;
import javax.tools.JavaFileObject;

import dagger.Module;
import dagger.Provides;
import me.emmano.scopesapi.Scope;
import retrofit.RestAdapter;


/**
 * Created by emmanuelortiguela on 12/26/14.
 */
@SupportedSourceVersion(SourceVersion.RELEASE_7)
@SupportedAnnotationTypes("me.emmano.scopesapi.Scope")
public class ScopesProcessor extends AbstractProcessor {

    //TODO Refactor the world. This code is bad.
    //TODO Support ButterKnife
    //TODO Add Annotation to support ApplicationGraph
    //TODO expand to allow more than one @Scope
    @Override
    public boolean process(Set<? extends TypeElement> annotations, RoundEnvironment roundEnv) {

        final Set<? extends Element> annotatedElements = roundEnv
                .getElementsAnnotatedWith(Scope.class);
        Set<String> toInjectOnBaseClass = new HashSet<>();
        if (annotatedElements.size() > 0) {
            for (Element annotatedElement : annotatedElements) {

                String newSourceName = "";
                final String packageName = processingEnv.getElementUtils()
                        .getPackageOf(annotatedElement).toString();

                final Scope scopeAnnotation = annotatedElement.getAnnotation(Scope.class);
                final String restAdapterClassName = scopeAnnotation
                        .restAdapterModule();
                final boolean enableButterKnife = scopeAnnotation.butterKnife();
                //Terrible solution; it works for now.
                try {
                    scopeAnnotation.retrofitServices();
                } catch (MirroredTypesException e) {
                    newSourceName = scopeAnnotation.baseActivityName();
                    for (TypeMirror typeMirror : e.getTypeMirrors()) {
                        toInjectOnBaseClass.add(typeMirror.toString());
                    }
                }

                try {
                    final String moduleName = newSourceName + "Module";
                    JavaWriter moduleWriter = createWriter(moduleName);
                    // Create different sections of the actual compile-time generated .java source
                    createHeader(false, packageName, moduleWriter, toInjectOnBaseClass);

                    final String fqName = packageName + "." + newSourceName;
                    emitModuleAnnotation(fqName, restAdapterClassName, moduleWriter);
                    beginType(moduleName, moduleWriter);
                    emitProviders(toInjectOnBaseClass, moduleWriter);
                    emitClose(moduleWriter);

                    JavaWriter activityWriter = createWriter(newSourceName);
                    createHeader(enableButterKnife, packageName, activityWriter);
                    beginType(newSourceName, activityWriter);
                    emitInjections(toInjectOnBaseClass, activityWriter);
                    emitOnCreate(enableButterKnife, fqName + "Module", activityWriter);
                    emitGetModules(activityWriter);
                    if (enableButterKnife) {
                        emitGetActivity(activityWriter);
                        emitGetLayout(activityWriter);
                    }
                    emitClose(activityWriter);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        return false;
    }

    private void emitGetLayout(JavaWriter activityWriter) throws IOException {
        activityWriter.beginMethod("int", "getLayout",
                EnumSet.of(Modifier.PROTECTED, Modifier.ABSTRACT));
        activityWriter.endMethod();
    }

    private void emitGetActivity(JavaWriter activityWriter) throws IOException {
        activityWriter.beginMethod("Activity", "getActivity",
                EnumSet.of(Modifier.PROTECTED, Modifier.ABSTRACT));
        activityWriter.endMethod();
    }

    private void emitInjections(Set<String> toInjectOnBaseClass, JavaWriter activityWriter)
            throws IOException {
        for (String toInject : toInjectOnBaseClass) {
            final String[] split = toInject.split(Pattern.quote("."));
            final String className = split[split.length - 1];
            activityWriter.emitAnnotation(Inject.class);
            activityWriter.emitField(toInject, className.substring(0, 1).toLowerCase()
                    + className.substring(1), EnumSet.of(Modifier.PROTECTED));
            activityWriter.emitEmptyLine();
        }
    }

    private void emitGetModules(JavaWriter activityWriter) throws IOException {
        activityWriter.beginMethod("Object[]", "getModules",
                EnumSet.of(Modifier.PROTECTED, Modifier.ABSTRACT));
        activityWriter.endMethod();

    }

    private void emitOnCreate(boolean enableButterKnife, String fqName, JavaWriter activityWriter)
            throws IOException {
        activityWriter.emitAnnotation(Override.class);
        activityWriter.beginMethod("void", "onCreate", EnumSet.of(Modifier.PROTECTED), "Bundle",
                "savedInstanceState");
        activityWriter.emitStatement("super.onCreate(savedInstanceState)");
        if (enableButterKnife) {
            activityWriter.emitStatement("setContentView(getLayout())");
            activityWriter.emitStatement("ButterKnife.inject(getActivity())");
        }
        activityWriter.emitStatement(
                "ObjectGraph.create(new " + fqName + "()).plus(getModules()).inject(this)");
        activityWriter.endMethod();

    }

    //TODO emit onDestroy to release ObjectGraph.

    private void emitProviders(Set<String> toInjectOnBaseClass, JavaWriter writer)
            throws IOException {
        for (String toInject : toInjectOnBaseClass) {
            final String[] split = toInject.split(Pattern.quote("."));
            final String className = split[split.length - 1];
            writer.emitAnnotation(Provides.class);
            writer.beginMethod(toInject, "provides" + className, EnumSet.of(Modifier.PUBLIC),
                    "RestAdapter", "adapter");
            writer.emitStatement("return adapter.create(" + toInject + ".class)");
            writer.endMethod();
        }
    }

    private void emitModuleAnnotation(String fqn, String adapterModule, JavaWriter writer)
            throws IOException {
        Map<String, String> classToInject = new HashMap<>(1);
        classToInject.put("injects", fqn + ".class");
        classToInject.put("includes", adapterModule);
        writer.emitAnnotation(Module.class, classToInject);
    }


    private JavaWriter createWriter(String newSourceName) throws IOException {
        final JavaFileObject sourceFile = processingEnv.getFiler()
                .createSourceFile(newSourceName);
        return new JavaWriter(sourceFile.openWriter());
    }

    private void createHeader(boolean enableButterKnife, String packageName,
            JavaWriter writer, Set<String>... toInjectOnBaseClass)
            throws IOException {
        writer.emitPackage(packageName);
        if (toInjectOnBaseClass.length > 0) {
            writer.emitImports(toInjectOnBaseClass[0]);
            writer.emitImports(RestAdapter.class);
        } else {
            writer.emitImports("android.app.Activity", "android.os.Bundle", "dagger.ObjectGraph");
            if (enableButterKnife) {
                writer.emitImports("butterknife.ButterKnife");
            }
        }
    }

    private void beginType(String newSourceName, JavaWriter writer) throws IOException {
        if (newSourceName.contains("Module")) {
            writer.beginType(newSourceName, "class", EnumSet.of(Modifier.PUBLIC));
        } else {
            writer.beginType(newSourceName, "class", EnumSet.of(Modifier.PUBLIC, Modifier.ABSTRACT),
                    "android.app.Activity");
        }
    }

    private void emitClose(JavaWriter writer) throws IOException {
        writer.endType();
        writer.close();
    }

}
