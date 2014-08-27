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

package org.capedwarf.phoner.server.mvc;

import java.util.List;

import com.google.api.server.spi.config.Api;
import com.google.api.server.spi.config.ApiMethod;
import com.google.api.server.spi.config.Named;
import org.capedwarf.phoner.server.dao.Contact;
import org.capedwarf.phoner.server.dao.ContactDao;

/**
 * @author <a href="mailto:ales.justin@jboss.org">Ales Justin</a>
 */
@Api(
    name = SyncEndpoint.NAME,
    version = SyncEndpoint.VERSION)
public class SyncEndpoint {
    public static final String NAME = "sync";
    public static final String VERSION = "v1";

    private final ContactDao dao;

    public SyncEndpoint() {
        dao = new ContactDao();
    }

    @ApiMethod(name = "sync.register", httpMethod = ApiMethod.HttpMethod.POST)
    public Response register(@Named("username") String username) {
        return new Response(dao.register(username));
    }

    @ApiMethod(name = "sync.push", httpMethod = ApiMethod.HttpMethod.PUT)
    public Response push(@Named("username") String username, Contact contact) {
        dao.addContact(username, contact);
        return Response.OK;
    }

    @ApiMethod(name = "sync.contacts", httpMethod = ApiMethod.HttpMethod.GET)
    public List<Contact> contacts(@Named("username") String username) {
        return dao.getContacts(username);
    }
}
