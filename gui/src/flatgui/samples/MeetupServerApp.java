/*
 * Copyright (c) 2015 Denys Lebediev and contributors. All rights reserved.
 * The use and distribution terms for this software are covered by the
 * Eclipse Public License 1.0 (http://opensource.org/licenses/eclipse-1.0.php)
 * which can be found in the file LICENSE at the root of this distribution.
 * By using this software in any fashion, you are agreeing to be bound by
 * the terms of this license.
 * You must not remove this notice, or any other, from this software.
 */

package flatgui.samples;

import clojure.lang.RT;
import clojure.lang.Var;
import flatgui.core.*;
import flatgui.core.websocket.FGAppServer;

import java.awt.*;
import java.io.File;
import java.io.StringReader;
import java.net.URL;
import java.util.Scanner;

/**
 * @author Denis Lebedev
 */
public class MeetupServerApp
{
    private static final String CONTAINER_SRC = "flatgui/samples/meetup.clj";
    private static final String CONTAINER_NS = "flatgui.samples.meetup";
    private static final String CONTAINER_VAR_NAME = "main";
    private static final int PORT = 13100;
    private static final String DESIGNER_APP_MAPPING = "designer";

    private static final String CONNECTOR_SRC = "flatgui/samples/backendconnector.clj";
    private static final String CONNECTOR_NS = "flatgui.samples.backendconnector";
    private static final String CONNECT_CONTAINER_VAR_NAME = "connect-container";


    public static void main(String[] args) throws Exception
    {
        URL formUrl = ClassLoader.getSystemResource(CONTAINER_SRC);
        String sourceCode = new Scanner(new File(formUrl.toURI())).useDelimiter("\\Z").next();

        IFGTemplate mainPanelTemplate = new FGTemplate(sourceCode, CONTAINER_NS, CONTAINER_VAR_NAME);

        URL connectorUrl = ClassLoader.getSystemResource(CONNECTOR_SRC);
        String connectorSrc = new Scanner(new File(connectorUrl.toURI())).useDelimiter("\\Z").next();
        clojure.lang.Compiler.load(new StringReader(connectorSrc));
        Var connectContainer = RT.var(CONNECTOR_NS, CONNECT_CONTAINER_VAR_NAME);

        FGAppServer server = new FGAppServer(mainPanelTemplate, PORT, DESIGNER_APP_MAPPING, connectContainer::invoke);

        server.start();
        server.join();
    }
}
