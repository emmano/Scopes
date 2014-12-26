package me.emmano.scopes.processor;

import com.squareup.javawriter.JavaWriter;

import java.io.IOException;
import java.util.EnumSet;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.RoundEnvironment;
import javax.annotation.processing.SupportedAnnotationTypes;
import javax.annotation.processing.SupportedSourceVersion;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.Element;
import javax.lang.model.element.Modifier;
import javax.lang.model.element.TypeElement;
import javax.lang.model.type.MirroredTypesException;
import javax.lang.model.type.TypeMirror;
import javax.tools.JavaFileObject;

import dagger.Module;
import me.emmano.scopesapi.Scope;


/**
 * Created by emmanuelortiguela on 12/26/14.
 */
@SupportedSourceVersion(SourceVersion.RELEASE_7)
@SupportedAnnotationTypes("me.emmano.scopesapi.Scope")
public class ScopesProcessor extends AbstractProcessor {

    @Override
    public boolean process(Set<? extends TypeElement> annotations, RoundEnvironment roundEnv) {

        final Set<? extends Element> annotatedElements = roundEnv
                .getElementsAnnotatedWith(Scope.class);
        Set<String> toInjectOnBaseClass = new HashSet<>();
        if(annotatedElements.size() > 0) {
            final Element firstAnnotatedElement = annotatedElements.iterator().next();
            String newSourceName = "";
            final String packageName = processingEnv.getElementUtils()
                    .getPackageOf(firstAnnotatedElement).toString();

            final Scope scopeAnnotation;

            try {
                firstAnnotatedElement.getAnnotation(Scope.class).classesToInject();
            } catch (MirroredTypesException e) {
                scopeAnnotation = firstAnnotatedElement.getAnnotation(Scope.class);
                newSourceName = scopeAnnotation.baseActivityName();
                for (TypeMirror typeMirror : e.getTypeMirrors()) {
                    toInjectOnBaseClass.add(typeMirror.toString());
                }
            }

            try {

                JavaWriter writer = createWriter(newSourceName + "Module");
                // Create different sections of the actual compile-time generated .java source
                createHeader(packageName, writer);
                emitModuleAnnotation(newSourceName, writer);
                beginType(newSourceName, writer);
                emitProviders(toInjectOnBaseClass, writer);

//            beginMethod(hostActivityName, writer);
//            emitStatements(annotatedElements, hostActivityName, writer);
                emitClose(writer);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        return false;
    }

    private void emitProviders(Set<String> toInjectOnBaseClass, JavaWriter writer) throws IOException{
        for (String toInjectOnBaseClas : toInjectOnBaseClass) {
            writer.beginMethod("void", "provide" + toInjectOnBaseClas, EnumSet.of(Modifier.PUBLIC));
            writer.endMethod();

        }
    }

    private void emitModuleAnnotation(String baseClassName, JavaWriter writer) throws IOException {
        Map<String, String> classToInject = new HashMap<>(1);
        classToInject.put("injects", baseClassName + ".class");
        writer.emitAnnotation(Module.class, classToInject);
    }


    private JavaWriter createWriter(String newSourceName) throws IOException {
        final JavaFileObject sourceFile = processingEnv.getFiler()
                .createSourceFile(newSourceName);
        return new JavaWriter(sourceFile.openWriter());
    }

    private void createHeader(String packageName,
            JavaWriter writer)
            throws IOException {
        writer.emitPackage(packageName);


    }

    private void beginType(String newSourceName, JavaWriter writer) throws IOException {
        writer.beginType(newSourceName, "class");
    }

    private void emitClose(JavaWriter writer) throws IOException {
        writer.endType();
        writer.close();
    }

}
