/*
 * JBoss, Home of Professional Open Source.
 * Copyright 2014, Red Hat, Inc., and individual contributors
 * as indicated by the @author tags. See the copyright.txt file in the
 * distribution for a full listing of individual contributors.
 *
 * This is free software; you can redistribute it and/or modify it
 * under the terms of the GNU Lesser General Public License as
 * published by the Free Software Foundation; either version 2.1 of
 * the License, or (at your option) any later version.
 *
 * This software is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with this software; if not, write to the Free
 * Software Foundation, Inc., 51 Franklin St, Fifth Floor, Boston, MA
 * 02110-1301 USA, or see the FSF site: http://www.fsf.org.
 */

package org.capedwarf.phoner.server.dao;

import java.util.ArrayList;
import java.util.List;

import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.PreparedQuery;
import com.google.appengine.api.datastore.Query;
import com.google.appengine.api.datastore.Transaction;

/**
 * @author <a href="mailto:ales.justin@jboss.org">Ales Justin</a>
 */
public class ContactDao {
    private static final String USERS = "Users";
    private static final String CONTACTS = "Contacts";

    private final DatastoreService datastore;

    public ContactDao() {
        datastore = DatastoreServiceFactory.getDatastoreService();
    }

    public long register(String username) {
        final Transaction tx = datastore.beginTransaction();
        try {
            Query query = new Query(USERS).setFilter(new Query.FilterPredicate("username", Query.FilterOperator.EQUAL, username)).setKeysOnly();
            PreparedQuery pq = datastore.prepare(query);
            if (pq.asIterator().hasNext()) {
                return 0;
            }
            Entity entity = new Entity(USERS);
            entity.setProperty("username", username);
            Key key = datastore.put(tx, entity);
            tx.commit();
            return key.getId();
        } catch (Throwable t) {
            tx.rollback();
            return -1;
        }
    }

    public void addContact(String username, Contact contact) {
        Entity entity = new Entity(CONTACTS);
        entity.setProperty("username", username);
        entity.setProperty("info", contact.getInfo());
        entity.setProperty("number", contact.getNumber());
        datastore.put(entity);
    }

    public List<Contact> getContacts(String username) {
        List<Contact> contacts = new ArrayList<>();
        Query query = new Query(CONTACTS).setFilter(new Query.FilterPredicate("username", Query.FilterOperator.EQUAL, username));
        PreparedQuery pq = datastore.prepare(query);
        for (Entity e : pq.asIterable()) {
            Contact contact = new Contact((String) e.getProperty("info"), (String) e.getProperty("number"));
            contacts.add(contact);
        }
        return contacts;
    }
}
