package me.emmano.scopes.app.models;

import javax.inject.Inject;

/**
 * Created by emmanuelortiguela on 12/27/14.
 */
public class DummyModel {

    @Inject
    public DummyModel() {
    }

    @Override
    public String toString() {
        return "I am a Dummy Object that likes to be injected by Scopes";
    }
}
