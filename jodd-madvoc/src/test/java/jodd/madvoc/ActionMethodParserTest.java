// Copyright (c) 2003-present, Jodd Team (http://jodd.org)
// All rights reserved.
//
// Redistribution and use in source and binary forms, with or without
// modification, are permitted provided that the following conditions are met:
//
// 1. Redistributions of source code must retain the above copyright notice,
// this list of conditions and the following disclaimer.
//
// 2. Redistributions in binary form must reproduce the above copyright
// notice, this list of conditions and the following disclaimer in the
// documentation and/or other materials provided with the distribution.
//
// THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS"
// AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE
// IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE
// ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT HOLDER OR CONTRIBUTORS BE
// LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR
// CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF
// SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS
// INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN
// CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE)
// ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE
// POSSIBILITY OF SUCH DAMAGE.

package jodd.madvoc;

import jodd.madvoc.component.ActionMethodParser;
import jodd.madvoc.component.ActionsManager;
import jodd.madvoc.config.ActionRuntime;
import jodd.madvoc.config.RouteChunk;
import jodd.madvoc.fixtures.tst.Boo1Action;
import jodd.madvoc.fixtures.tst.Boo2Action;
import jodd.madvoc.fixtures.tst.Boo3Action;
import jodd.madvoc.fixtures.tst.BooAction;
import jodd.madvoc.fixtures.tst2.Boo4Action;
import jodd.madvoc.fixtures.tst2.Boo5Action;
import jodd.madvoc.fixtures.tst2.ReAction;
import jodd.madvoc.macro.RegExpPathMacros;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

class ActionMethodParserTest extends MadvocTestCase {

	@Test
	void testDefaultMethods() {
		WebApp webapp = new WebApp();
		webapp.start();

		ActionMethodParser actionMethodParser = webapp.madvocContainer().lookupComponent(ActionMethodParser.class);

		ActionRuntime cfg = parse(actionMethodParser, "fixtures.tst.BooAction#foo");
		assertEquals("/boo.foo", cfg.actionPath());

		cfg = parse(actionMethodParser, "fixtures.tst.BooAction#view");
		assertEquals("/boo", cfg.actionPath());

		cfg = parse(actionMethodParser, "fixtures.tst.BooAction#execute");
		assertEquals("/boo", cfg.actionPath());

	}

	@Test
	void testMethod() {
		WebApp webapp = new WebApp();
		webapp.start();

		ActionMethodParser actionMethodParser = webapp.madvocContainer().lookupComponent(ActionMethodParser.class);

		ActionRuntime cfg = parse(actionMethodParser, "fixtures.tst.BooAction#foo");
		assertNotNull(cfg);
		assertEquals(BooAction.class, cfg.actionClass());
		assertEquals("/boo.foo", cfg.actionPath());

		cfg = parse(actionMethodParser, "fixtures.tst.BooAction#foo1");
		assertEquals("/boo.xxx", cfg.actionPath());

		cfg = parse(actionMethodParser, "fixtures.tst.BooAction#foo2");
		assertEquals("/boo.foo2.xxx", cfg.actionPath());

		cfg = parse(actionMethodParser, "fixtures.tst.BooAction#foo3");
		assertEquals("/boo", cfg.actionPath());

		cfg = parse(actionMethodParser, "fixtures.tst.BooAction#foo4");
		assertEquals("/xxx", cfg.actionPath());
		assertNull(cfg.actionMethod());

		cfg = parse(actionMethodParser, "fixtures.tst.BooAction#foo41");
		assertEquals("/xxx", cfg.actionPath());
		assertEquals("DELETE", cfg.actionMethod());

		cfg = parse(actionMethodParser, "fixtures.tst.BooAction#foo5");
		assertEquals("/xxx.html", cfg.actionPath());
		assertEquals("POST", cfg.actionMethod());

		ActionsManager actionsManager = webapp.madvocContainer().lookupComponent(ActionsManager.class);
		assertEquals("/xxx.html", actionsManager.lookupPathAlias("dude"));
	}

	@Test
	void testMethodWithPackage() {
		WebApp webapp = new WebApp();
		webapp.start();

		ActionMethodParser actionMethodParser = webapp.madvocContainer().lookupComponent(ActionMethodParser.class);
		MadvocConfig madvocConfig = webapp.madvocContainer().lookupComponent(MadvocConfig.class);
		madvocConfig.getRootPackages().addRootPackageOf(this.getClass());

		ActionRuntime cfg = parse(actionMethodParser, "fixtures.tst.BooAction#foo");
		assertNotNull(cfg);
		assertEquals(BooAction.class, cfg.actionClass());
		assertEquals("/fixtures/tst/boo.foo", cfg.actionPath());

		cfg = parse(actionMethodParser, "fixtures.tst.BooAction#foo1");
		assertEquals("/fixtures/tst/boo.xxx", cfg.actionPath());

		cfg = parse(actionMethodParser, "fixtures.tst.BooAction#foo2");
		assertEquals("/fixtures/tst/boo.foo2.xxx", cfg.actionPath());

		cfg = parse(actionMethodParser, "fixtures.tst.BooAction#foo3");
		assertEquals("/fixtures/tst/boo", cfg.actionPath());

		cfg = parse(actionMethodParser, "fixtures.tst.BooAction#foo4");
		assertEquals("/xxx", cfg.actionPath());
		assertNull(cfg.actionMethod());

		cfg = parse(actionMethodParser, "fixtures.tst.BooAction#foo41");
		assertEquals("/xxx", cfg.actionPath());
		assertEquals("DELETE", cfg.actionMethod());

		cfg = parse(actionMethodParser, "fixtures.tst.BooAction#foo5");
		assertEquals("/xxx.html", cfg.actionPath());
		assertEquals("POST", cfg.actionMethod());

		cfg = parse(actionMethodParser, "fixtures.tst.BooAction#foo6");
		assertEquals("/fixtures/tst/boo.qfoo62", cfg.actionPath());

		cfg = parse(actionMethodParser, "fixtures.tst.BooAction#foo7");
		assertEquals("/foo7.html", cfg.actionPath());
	}


	@Test
	void testClasses() {
		WebApp webapp = new WebApp();
		webapp.start();

		ActionMethodParser actionMethodParser = webapp.madvocContainer().lookupComponent(ActionMethodParser.class);

		ActionRuntime cfg = parse(actionMethodParser, "fixtures.tst.Boo1Action#foo");
		assertNotNull(cfg);
		assertEquals(Boo1Action.class, cfg.actionClass());
		assertEquals("/boo1.foo", cfg.actionPath());

		cfg = parse(actionMethodParser, "fixtures.tst.Boo2Action#foo");
		assertNotNull(cfg);
		assertEquals(Boo2Action.class, cfg.actionClass());
		assertEquals("/bbb.foo", cfg.actionPath());

		cfg = parse(actionMethodParser, "fixtures.tst.Boo2Action#foo1");
		assertEquals("/bbb.xxx", cfg.actionPath());

		cfg = parse(actionMethodParser, "fixtures.tst.Boo2Action#foo2");
		assertEquals("/bbb.foo2.xxx", cfg.actionPath());

		cfg = parse(actionMethodParser, "fixtures.tst.Boo2Action#foo3");
		assertEquals("/bbb", cfg.actionPath());

	}

	@Test
	void testClassesWithPackage() {
		WebApp webapp = new WebApp();
		webapp.start();

		ActionMethodParser actionMethodParser = webapp.madvocContainer().lookupComponent(ActionMethodParser.class);
		MadvocConfig madvocConfig = webapp.madvocContainer().lookupComponent(MadvocConfig.class);
		madvocConfig.getRootPackages().addRootPackageOf(this.getClass());

		ActionRuntime cfg = parse(actionMethodParser, "fixtures.tst.Boo1Action#foo");
		assertNotNull(cfg);
		assertEquals(Boo1Action.class, cfg.actionClass());
		assertEquals("/fixtures/tst/boo1.foo", cfg.actionPath());

		cfg = parse(actionMethodParser, "fixtures.tst.Boo2Action#foo");
		assertNotNull(cfg);
		assertEquals(Boo2Action.class, cfg.actionClass());
		assertEquals("/fixtures/tst/bbb.foo", cfg.actionPath());

		cfg = parse(actionMethodParser, "fixtures.tst.Boo2Action#foo1");
		assertEquals("/fixtures/tst/bbb.xxx", cfg.actionPath());

		cfg = parse(actionMethodParser, "fixtures.tst.Boo2Action#foo2");
		assertEquals("/fixtures/tst/bbb.foo2.xxx", cfg.actionPath());

		cfg = parse(actionMethodParser, "fixtures.tst.Boo2Action#foo3");
		assertEquals("/fixtures/tst/bbb", cfg.actionPath());

	}

	@Test
	void testClassesWithoutPackage() {
		WebApp webapp = new WebApp();
		webapp.start();

		ActionMethodParser actionMethodParser = webapp.madvocContainer().lookupComponent(ActionMethodParser.class);
		MadvocConfig madvocConfig = webapp.madvocContainer().lookupComponent(MadvocConfig.class);
		madvocConfig.getRootPackages().addRootPackageOf(this.getClass());

		ActionRuntime cfg = parse(actionMethodParser, "fixtures.tst.Boo3Action#foo");
		assertNotNull(cfg);
		assertEquals(Boo3Action.class, cfg.actionClass());
		assertEquals("/bbb.foo", cfg.actionPath());

		cfg = parse(actionMethodParser, "fixtures.tst.Boo3Action#foo1");
		assertEquals("/bbb.xxx", cfg.actionPath());

		cfg = parse(actionMethodParser, "fixtures.tst.Boo3Action#foo2");
		assertEquals("/bbb.foo2.xxx", cfg.actionPath());

		cfg = parse(actionMethodParser, "fixtures.tst.Boo3Action#foo3");
		assertEquals("/bbb", cfg.actionPath());

	}

	@Test
	void testPackage() {
		WebApp webapp = new WebApp();
		webapp.start();

		ActionMethodParser actionMethodParser = webapp.madvocContainer().lookupComponent(ActionMethodParser.class);
		MadvocConfig madvocConfig = webapp.madvocContainer().lookupComponent(MadvocConfig.class);
		madvocConfig.getRootPackages().addRootPackageOf(this.getClass());

		ActionRuntime cfg = parse(actionMethodParser, "fixtures.tst2.Boo4Action#foo");
		assertNotNull(cfg);
		assertEquals(Boo4Action.class, cfg.actionClass());
		assertEquals("/ttt/www.foo", cfg.actionPath());

		cfg = parse(actionMethodParser, "fixtures.tst2.Boo4Action#foo1");
		assertEquals("/ttt/www.xxx", cfg.actionPath());

		cfg = parse(actionMethodParser, "fixtures.tst2.Boo4Action#foo2");
		assertEquals("/ttt/www.foo2.xxx", cfg.actionPath());

		cfg = parse(actionMethodParser, "fixtures.tst2.Boo4Action#foo3");
		assertEquals("/ttt/www", cfg.actionPath());

	}

	@Test
	void testNoPackage() {
		WebApp webapp = new WebApp();
		webapp.start();

		ActionMethodParser actionMethodParser = webapp.madvocContainer().lookupComponent(ActionMethodParser.class);
		MadvocConfig madvocConfig = webapp.madvocContainer().lookupComponent(MadvocConfig.class);
		madvocConfig.getRootPackages().addRootPackageOf(this.getClass());

		ActionRuntime cfg = parse(actionMethodParser, "fixtures.tst2.Boo5Action#foo");
		assertNotNull(cfg);
		assertEquals(Boo5Action.class, cfg.actionClass());
		assertEquals("/www.foo", cfg.actionPath());

		cfg = parse(actionMethodParser, "fixtures.tst2.Boo5Action#foo1");
		assertEquals("/www.xxx", cfg.actionPath());

		cfg = parse(actionMethodParser, "fixtures.tst2.Boo5Action#foo2");
		assertEquals("/www.foo2.xxx", cfg.actionPath());

		cfg = parse(actionMethodParser, "fixtures.tst2.Boo5Action#foo3");
		assertEquals("/www", cfg.actionPath());

	}

	@Test
	void testEndSlashClassName() {
		WebApp webapp = new WebApp();
		webapp.start();

		ActionMethodParser actionMethodParser = webapp.madvocContainer().lookupComponent(ActionMethodParser.class);
		MadvocConfig madvocConfig = webapp.madvocContainer().lookupComponent(MadvocConfig.class);
		madvocConfig.getRootPackages().addRootPackageOf(this.getClass());

		ActionRuntime cfg = parse(actionMethodParser, "fixtures.tst2.ReAction#hello");
		assertNotNull(cfg);
		assertEquals(ReAction.class, cfg.actionClass());
		assertEquals("/re/hello", cfg.actionPath());

		cfg = parse(actionMethodParser, "fixtures.tst2.ReAction#macro");
		assertNotNull(cfg);
		assertEquals(ReAction.class, cfg.actionClass());
		assertEquals("/re/user/{id}/macro", cfg.actionPath());
	}

	@Test
	void testMacros() {
		WebApp webapp = new WebApp();
		webapp.start();
		
		ActionsManager actionsManager = webapp.madvocContainer().lookupComponent(ActionsManager.class);
		MadvocConfig madvocConfig = webapp.madvocContainer().lookupComponent(MadvocConfig.class);
		madvocConfig.getRootPackages().addRootPackageOf(this.getClass());

		actionsManager.registerAction(ReAction.class, "macro", null);
		ActionRuntime cfg = actionsManager.lookup("GET", MadvocUtil.splitPathToChunks("/re/user/173/macro.html"));

		assertNotNull(cfg);
		assertEquals(ReAction.class, cfg.actionClass());
		assertEquals("/re/user/{id}/macro", cfg.actionPath());

		RouteChunk chunk = cfg.routeChunk();
		assertNull(chunk.pathMacros());
		chunk = chunk.parent();
		assertEquals(1, chunk.pathMacros().macrosCount());
		assertEquals("id", chunk.pathMacros().names()[0]);
		assertNull(chunk.pathMacros().patterns()[0]);


		actionsManager.registerAction(ReAction.class, "macro2", null);
		cfg = actionsManager.lookup("GET", MadvocUtil.splitPathToChunks("/re/user/image/173/png/macro2.html"));

		assertNotNull(cfg);
		assertEquals(ReAction.class, cfg.actionClass());
		assertEquals("/re/user/image/{id}/{fmt}/macro2", cfg.actionPath());
		chunk = cfg.routeChunk();
		assertNull(chunk.pathMacros());
		chunk = chunk.parent();
		assertEquals(1, chunk.pathMacros().macrosCount());
		assertEquals("fmt", chunk.pathMacros().names()[0]);
		chunk = chunk.parent();
		assertEquals(1, chunk.pathMacros().macrosCount());
		assertEquals("id", chunk.pathMacros().names()[0]);
		chunk = cfg.routeChunk();
		assertNull(chunk.pathMacros());

		actionsManager.registerAction(ReAction.class, "macro3", null);
		cfg = actionsManager.lookup("POST", MadvocUtil.splitPathToChunks("/re/users/173/macro3"));

		assertNotNull(cfg);
		assertEquals(ReAction.class, cfg.actionClass());
		assertEquals("/re/users/{id}/macro3", cfg.actionPath());
		assertEquals("POST", cfg.actionMethod());
		chunk = cfg.routeChunk();
		assertNull(chunk.pathMacros());
		chunk = chunk.parent();
		assertEquals(1, chunk.pathMacros().macrosCount());
		assertEquals("id", chunk.pathMacros().names()[0]);

		cfg = actionsManager.lookup("GET", MadvocUtil.splitPathToChunks("/re/user/index.html"));
		assertNull(cfg);

		cfg = actionsManager.lookup("GET", MadvocUtil.splitPathToChunks("/re/user/index/reindex/macro.html"));
		assertNull(cfg);

		cfg = actionsManager.lookup("GET", MadvocUtil.splitPathToChunks("/re/users/173/macro3"));
		assertNull(cfg);

		assertEquals(3, actionsManager.getActionsCount());
	}

	@Test
	void testMacrosWildcards() {
		WebApp webapp = new WebApp();
		webapp.start();

		ActionsManager actionsManager = webapp.madvocContainer().lookupComponent(ActionsManager.class);
		MadvocConfig madvocConfig = webapp.madvocContainer().lookupComponent(MadvocConfig.class);
		madvocConfig.getRootPackages().addRootPackageOf(this.getClass());

		actionsManager.registerAction(ReAction.class, "wild1", null);
		actionsManager.registerAction(ReAction.class, "wild2", null);

		ActionRuntime cfg = actionsManager.lookup("GET", MadvocUtil.splitPathToChunks("/re/ild123cat"));
		assertNull(cfg);

		cfg = actionsManager.lookup("GET", MadvocUtil.splitPathToChunks("/re/wild123ca"));
		assertNull(cfg);

		cfg = actionsManager.lookup("GET", MadvocUtil.splitPathToChunks("/re/wild123cat.html"));
		assertNotNull(cfg);
		assertEquals(ReAction.class, cfg.actionClass());
		assertEquals("/re/wild{id}cat", cfg.actionPath());

		RouteChunk chunk = cfg.routeChunk();
		assertEquals(1, chunk.pathMacros().macrosCount());
		assertEquals("id", chunk.pathMacros().names()[0]);

		cfg = actionsManager.lookup("GET", MadvocUtil.splitPathToChunks("/re/wild123dog.html"));
		assertNull(cfg);

		cfg = actionsManager.lookup("POST", MadvocUtil.splitPathToChunks("/re/wild123dog.html"));
		assertNotNull(cfg);
		assertEquals(ReAction.class, cfg.actionClass());
		assertEquals("/re/wild{id}dog", cfg.actionPath());
		assertEquals("POST", cfg.actionMethod());
		chunk = cfg.routeChunk();
		assertEquals(1, chunk.pathMacros().macrosCount());
		assertEquals("id", chunk.pathMacros().names()[0]);

		assertEquals(2, actionsManager.getActionsCount());
	}

	@Test
	void testMacrosDups() {
		WebApp webapp = new WebApp();
		webapp.start();

		ActionsManager actionsManager = webapp.madvocContainer().lookupComponent(ActionsManager.class);

		MadvocConfig madvocConfig = webapp.madvocContainer().lookupComponent(MadvocConfig.class);
		madvocConfig.getRootPackages().addRootPackageOf(this.getClass());
		madvocConfig.setPathMacroClass(RegExpPathMacros.class);

		actionsManager.registerAction(ReAction.class, "duplo2", null);
		actionsManager.registerAction(ReAction.class, "duplo1", null);

		ActionRuntime cfg = actionsManager.lookup("GET", MadvocUtil.splitPathToChunks("/re/duplo/123"));
		assertNotNull(cfg);
		assertEquals(ReAction.class, cfg.actionClass());
		assertEquals("/re/duplo/{id:^[0-9]+}", cfg.actionPath());

		RouteChunk chunk = cfg.routeChunk();
		assertEquals(1, chunk.pathMacros().macrosCount());
		assertEquals("id", chunk.pathMacros().names()[0]);

		cfg = actionsManager.lookup("GET", MadvocUtil.splitPathToChunks("/re/duplo/aaa"));
		assertNotNull(cfg);
		assertEquals(ReAction.class, cfg.actionClass());
		assertEquals("/re/duplo/{sid}", cfg.actionPath());
		chunk = cfg.routeChunk();
		assertEquals(1, chunk.pathMacros().macrosCount());
		assertEquals("sid", chunk.pathMacros().names()[0]);

		assertEquals(2, actionsManager.getActionsCount());
	}

	@Test
	void testZqq() {
		WebApp webapp = new WebApp();
		webapp.start();

		ActionsManager actionsManager = webapp.madvocContainer().lookupComponent(ActionsManager.class);
		MadvocConfig madvocConfig = webapp.madvocContainer().lookupComponent(MadvocConfig.class);
		madvocConfig.getRootPackages().addRootPackageOf(this.getClass());

		actionsManager.registerAction(ReAction.class, "zqq1", null);
		actionsManager.registerAction(ReAction.class, "zqq2", null);

		ActionRuntime cfg = actionsManager.lookup("GET", MadvocUtil.splitPathToChunks("/config/dba.delete_multi.do"));
		assertNotNull(cfg);

		assertEquals("/{entityName}/dba.delete_multi.do", cfg.actionPath());
	}

}
