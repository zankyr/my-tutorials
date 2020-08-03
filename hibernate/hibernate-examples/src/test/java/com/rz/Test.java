package com.rz;

import com.google.common.collect.ImmutableList;
import com.rz.model.IdentityGeneratedEntity;
import com.rz.model.Post;

import java.util.List;

public class Test extends AbstractTest {

    @Override
    public void setUp() {
        super.setUp();
    }

    @org.junit.Test
    public void whenInsertIdentity() {

        List<IdentityGeneratedEntity> identityGeneratedEntities = ImmutableList.of(
                new IdentityGeneratedEntity("value"),
                new IdentityGeneratedEntity("value1"),
                new IdentityGeneratedEntity("value2"));


        identityGeneratedEntities.forEach(session::persist);

        showTableContent("identity_gen_entity");
    }

    @org.junit.Test
    public void test() {

        showTableStructure("post");

        session.save(new Post("title"));
        session.save(new Post("title 1"));
        session.flush();

        showTableContent("post");

    }
}
