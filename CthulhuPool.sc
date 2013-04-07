/*
Live coding for OSCthulhu and Manticore
*/
CthulhuPool : ManticorePiece
{

	var bufferView, macroView;

	var osc;

	*new
	{
		^super.new.init;
	}

	*initClass
	{
		Manticore.registerPiece('CthulhuPool');	// register with Manticore
	}

	init
	{
		Manticore.group = "CP";
		Manticore.updateScore(
			"In a code buffer:\n"
			"  cmd-enter: execute all code\n"
			"  cmd-e: insert username-specific environmental variable at cursor\n"
			"  cmd-/: inserts // if nothing is selected, block (un)comment selection"
		);

		this.setupOSClisteners;

		OSCthulhu.changePorts(57120);
		OSCthulhu.login(Manticore.group);
	}

	setupOSClisteners
	{


		osc.collect({|def| def.permanent_(true); });
	}

	getInterface
	{|p|
		bufferView = CthulhuPoolBufferWindow.new;

		macroView = CthulhuPoolMacroWindow.new

		^p.layout_(
			HLayout(
				[bufferView.getView,stretch:5],
				[VLayout(
					StaticText().string_("Notepad"),
					[TextView(),stretch:3],
					StaticText().string_("Post Window"),
					[CthulhuPoolPostWindow.getView,stretch:3]
				),stretch:3],
				[macroView.getView,stretch:2]
			)
		);
	}

	free
	{
		bufferView.free;
		macroView.free;
		osc.collect({|def| def.permanent_(false); def.free; });
		OSCthulhu.cleanup(Manticore.group);
	}
}

/*
Side panel with macros
*/
CthulhuPoolMacroWindow
{
	var osc, macros, macroList;

	*new
	{
		^super.new.init;
	}

	init
	{
		osc = List.new;
		macros = Dictionary();

		this.setupOSClisteners;
	}

	setupOSClisteners
	{
		osc.add(
			OSCthulhu.onAddSyncObject(\addMacro,{|msg|
				var oscAddr, objName, group, subGroup, args;

				# oscAddr, objName, group, subGroup = msg;
				args = msg.copyRange(4,msg.size-1);

				if(subGroup.asString == "m", {
					if(macros.at(objName.asString).isNil,{
						macros.put(
							objName.asString,
							CthulhuPoolNewMacro.new(objName.asString,args[0].asString)
						);
						{macroList.canvas =  this.populateMacrosList(); }.defer;
					});
				});

			});
		);

		osc.add(
			OSCthulhu.onRemoveSyncObject(\removeMacro,{|msg|
				var oscAddr, objName, group, subGroup;

				# oscAddr, objName, group, subGroup = msg;

				if(subGroup.asString == "m", {
					macros.removeAt(objName.asString);
					{macroList.canvas =  this.populateMacrosList(); }.defer;
				});
			});
		);

		osc.collect({|def| def.permanent_(true); });
	}
	getView
	{
		var macroListView, addBufferBtn, addMacroBtn, cmdPeriodBtn;

		macroListView = View();
		/*
		View.globalKeyDownAction_({|view, char, modifiers, unicode, keycode, key|
		[key,modifiers].postln;
		});
		*/
		macroList = ScrollView();

		addBufferBtn = Button(macroListView);
		addBufferBtn.states_([
			["Add Code Buffer",Color.white,Color.green(0.35)]
		]);
		addBufferBtn.action_({|btn|
			OSCthulhu.addSyncObject(
				"b" ++ rrand(1000,9999),
				Manticore.group,
				"b",
				[
					"none",
					"",
					0
				]
			)
		});

		addMacroBtn = Button(macroListView);
		addMacroBtn.states_([
			["New Macro",Color.white,Color.green(0.35)]
		]);
		addMacroBtn.action_({|btn|
			CthulhuPoolNewMacroWindow.getView;
		});

		cmdPeriodBtn = Button(macroListView);
		cmdPeriodBtn.states_([["Cmd-.",Color.white,Color.red]]);
		cmdPeriodBtn.action_({ CmdPeriod.run; OSCthulhu.chat("*** CmdPeriod ***"); });

		macroListView.layout_(
			VLayout(
				addBufferBtn,
				addMacroBtn,
				StaticText().string_("Macros"),
				macroList,
				cmdPeriodBtn
			)
		);

		^macroListView;
	}

	populateMacrosList
	{
		var objList;

		objList = View();
		objList.layout_(VLayout());

		macros.keysDo{|key|
			objList.layout.add(
				macros.at(key).getView
			);
		};

		objList.layout.add(nil);

		^objList;
	}

	free
	{
		osc.collect({|def| def.permanent_(false); def.free; });
	}
}

CthulhuPoolNewMacroWindow
{
	*getView
	{
		var win, nameField, codeView, createBtn;

		win = Window("New Macro",Rect(20,20,400,200),false);
		win.front;

		nameField = TextField(win);

		codeView = TextView(win);

		createBtn = Button(win);
		createBtn.states_([
			["Create",Color.white,Color.red(0.6)]
		]);
		createBtn.action_({|btn|
			OSCthulhu.addSyncObject(
				nameField.string,
				Manticore.group,
				"m",
				[
					codeView.string
				]
			);
			win.close;
		});

		win.view.layout_(
			VLayout(
				HLayout(
					[StaticText().string_("Name:"),stretch:1],
					[nameField,stretch:8]
				),
				codeView,
				createBtn
			)
		);
	}
}

CthulhuPoolNewMacro
{
	var name, code;

	*new
	{|name,code|
		^super.newCopyArgs(name,code);
	}

	getView
	{
		var container, drag, closeBtn;

		container = View();

		drag = DragSource(container);
		drag.object_(code.asString);
		drag.background_(Color.rand);
		drag.string_(name);
		drag.dragLabel_(name);

		closeBtn = Button(container);
		closeBtn.states_([
			["X",Color.white,Color.gray(0.35)]
		]);
		closeBtn.action_({|btn|
			OSCthulhu.removeSyncObject(name);
		});

		container.layout_(
			HLayout(
				[drag,stretch:4],
				[closeBtn,stretch:1]
			)
		);

		^container;
	}
}

/*
Interface
*/
CthulhuPoolBufferWindow
{
	var osc, buffers, scrollView;

	*new
	{
		^super.new.init;
	}

	init
	{
		osc = List.new;
		buffers = Dictionary();

		this.setupOSClisteners;
	}

	setupOSClisteners
	{
		osc.add(
			OSCthulhu.onAddSyncObject(\addBuffer,{|msg|
				var oscAddr, objName, group, subGroup, args;

				# oscAddr, objName, group, subGroup = msg;
				args = msg.copyRange(4,msg.size-1);

				if(subGroup.asString == "b", {
					if(buffers.at(objName.asString).isNil,{
						buffers.put(
							objName.asString,
							CthulhuPoolCodeBuffer.new(
								objName.asString,
								args[0].asString,
								args[1].asString,
								args[2].asInteger
							)
						);
						{scrollView.canvas = this.populateBuffers();}.defer;
					});
				});

			});
		);

		osc.add(
			OSCthulhu.onSetSyncArg(\updateBuffer, {|msg|
				var oscAddr, objName, argIndex, argValue, group, subGroup;

				# oscAddr, objName, argIndex, argValue, group, subGroup = msg;

				if(subGroup.asString == "b", {
					switch(
						argIndex,
						0, { buffers.at(objName.asString).setSelectedBy(argValue.asString); },
						1, { buffers.at(objName.asString).setCode(argValue.asString); },
						2, { buffers.at(objName.asString).setRunCount(argValue); }
					);
				});
			});
		);

		osc.add(
			OSCthulhu.onRemoveSyncObject(\removeBuffer,{|msg|
				var oscAddr, objName, group, subGroup;

				# oscAddr, objName, group, subGroup = msg;

				if(subGroup.asString == "b", {
					buffers.removeAt(objName.asString);
					{scrollView.canvas = this.populateBuffers();}.defer;
				});

			});
		);

		osc.collect({|def| def.permanent_(true); });
	}

	getView
	{
		scrollView = ScrollView();

		{scrollView.canvas = this.populateBuffers();}.defer;

		^scrollView;
	}

	populateBuffers
	{
		var bufferView;

		bufferView = View();
		bufferView.layout_(VLayout());

		buffers.keysDo{|key,i|
			bufferView.layout.add( buffers.at(key).getView );
		};

		^bufferView;
	}

	free
	{
		osc.collect({|def| def.permanent_(false); def.free; });
	}
}

/*
Code editing buffer
*/
CthulhuPoolCodeBuffer
{
	var bufferName, selectedBy, code, runCount;
	var view, codeArea, editBtn, execBtn, closeBtn, bufferLengthText;

	*new
	{|bufferName, selectedBy="none", code="", runCount=0|
		^super.newCopyArgs(bufferName, selectedBy, code, runCount);
	}

	getView
	{
		view = View(bounds:Rect(100,100,400,300));
		view.minHeight_(200);
		view.background_(Color.rand);

		bufferLengthText = StaticText().string_("Chars: 0");

		// codeing area
		codeArea = TextView(view);
		codeArea.editable_(false);
		codeArea.string_(code);
		codeArea.keyDownAction_({|view, char, modifiers, unicode, keycode, key|
			//  [1048576, 82 ] cmd-r
			if([modifiers,key] == [ 1048576, 16777220 ],{ // cmd-enter
				execBtn.doAction;
			});
			/*
			if([modifiers,key] == [ 131072, 16777220 ],{ // shift-enter
				execBtn.doAction;
			});
			*/
			if([modifiers,key] == [ 262144, 16777220 ],{ // ctrl-enter
				execBtn.doAction;
			});


			if([modifiers,key] == [ 1048576, 69 ],{ // cmd-e
				codeArea.setString(
					"~e_" ++ OSCthulhu.userName ++ "[]",
					codeArea.selectionStart,
					0
				);
			});

			if([modifiers,key] == [ 262144, 69 ],{ // ctrl-e
				codeArea.setString(
					"~e_" ++ OSCthulhu.userName  ++ "[]",
					codeArea.selectionStart,
					0
				);
			});

			if([modifiers,key] == [ 1048576, 48 ],{ // cmd-0
				codeArea.setString(
					"~e_" ++ OSCthulhu.userName ++ "0",
					codeArea.selectionStart,
					0
				);
			});

			if([modifiers,key] == [ 262144, 48 ],{ // ctrl-0
				codeArea.setString(
					"~e_" ++ OSCthulhu.userName ++ "0",
					codeArea.selectionStart,
					0
				);
			});

			if([modifiers,key] == [ 1048576, 49 ],{ // cmd-1
				codeArea.setString(
					"~e_" ++ OSCthulhu.userName ++ "1",
					codeArea.selectionStart,
					0
				);
			});

			if([modifiers,key] == [ 262144, 49 ],{ // ctrl-1
				codeArea.setString(
					"~e_" ++ OSCthulhu.userName ++ "1",
					codeArea.selectionStart,
					0
				);
			});

			if([modifiers,key] == [ 1048576, 50 ],{ // cmd-2
				codeArea.setString(
					"~e_" ++ OSCthulhu.userName ++ "2",
					codeArea.selectionStart,
					0
				);
			});

			if([modifiers,key] == [ 262144, 50 ],{ // ctrl-2
				codeArea.setString(
					"~e_" ++ OSCthulhu.userName ++ "2",
					codeArea.selectionStart,
					0
				);
			});

			if([modifiers,key] == [ 1048576, 51 ],{ // cmd-3
				codeArea.setString(
					"~e_" ++ OSCthulhu.userName ++ "3",
					codeArea.selectionStart,
					0
				);
			});

			if([modifiers,key] == [ 262144, 51 ],{ // ctrl-3
				codeArea.setString(
					"~e_" ++ OSCthulhu.userName ++ "3",
					codeArea.selectionStart,
					0
				);
			});

			if([modifiers,key] == [ 1048576, 52 ],{ // cmd-4
				codeArea.setString(
					"~e_" ++ OSCthulhu.userName ++ "4",
					codeArea.selectionStart,
					0
				);
			});

			if([modifiers,key] == [ 262144, 52 ],{ // ctrl-4
				codeArea.setString(
					"~e_" ++ OSCthulhu.userName ++ "4",
					codeArea.selectionStart,
					0
				);
			});

			if([modifiers,key] == [ 1048576, 53 ],{ // cmd-5
				codeArea.setString(
					"~e_" ++ OSCthulhu.userName ++ "5",
					codeArea.selectionStart,
					0
				);
			});

			if([modifiers,key] == [ 262144, 53 ],{ // ctrl-5
				codeArea.setString(
					"~e_" ++ OSCthulhu.userName ++ "5",
					codeArea.selectionStart,
					0
				);
			});

			if([modifiers,key] == [ 1048576, 54 ],{ // cmd-6
				codeArea.setString(
					"~e_" ++ OSCthulhu.userName ++ "6",
					codeArea.selectionStart,
					0
				);
			});

			if([modifiers,key] == [ 262144, 54 ],{ // ctrl-6
				codeArea.setString(
					"~e_" ++ OSCthulhu.userName ++ "6",
					codeArea.selectionStart,
					0
				);
			});

			if([modifiers,key] == [ 1048576, 55 ],{ // cmd-7
				codeArea.setString(
					"~e_" ++ OSCthulhu.userName ++ "7",
					codeArea.selectionStart,
					0
				);
			});

			if([modifiers,key] == [ 262144, 55 ],{ // ctrl-7
				codeArea.setString(
					"~e_" ++ OSCthulhu.userName ++ "7",
					codeArea.selectionStart,
					0
				);
			});

			if([modifiers,key] == [ 1048576, 56 ],{ // cmd-8
				codeArea.setString(
					"~e_" ++ OSCthulhu.userName ++ "8",
					codeArea.selectionStart,
					0
				);
			});

			if([modifiers,key] == [ 262144, 56 ],{ // ctrl-8
				codeArea.setString(
					"~e_" ++ OSCthulhu.userName ++ "8",
					codeArea.selectionStart,
					0
				);
			});

			if([modifiers,key] == [ 1048576, 57 ],{ // cmd-9
				codeArea.setString(
					"~e_" ++ OSCthulhu.userName ++ "9",
					codeArea.selectionStart,
					0
				);
			});

			if([modifiers,key] == [ 262144, 57 ],{ // ctrl-9
				codeArea.setString(
					"~e_" ++ OSCthulhu.userName ++ "9",
					codeArea.selectionStart,
					0
				);
			});

			if([modifiers,key] == [ 1048576, 47 ],{ // cmd-/
				switch( codeArea.selectionSize,
					0, {
						codeArea.setString("//",codeArea.selectionStart,0);
					},
					{
						if(codeArea.selectedString.contains("/*") &&
							codeArea.selectedString.contains("*/"),{
								codeArea.selectedString = codeArea.selectedString.replace("/*","").replace("*/","");
							},{
								codeArea.setString("/*",codeArea.selectionStart,0);
								codeArea.setString("*/",codeArea.selectionStart+codeArea.selectionSize,0);
						});
					}
				);
			});

			if([modifiers,key] == [ 262144, 47 ],{ // ctrl-/
				switch( codeArea.selectionSize,
					0, {
						codeArea.setString("//",codeArea.selectionStart,0);
					},
					{
						if(codeArea.selectedString.contains("/*") &&
							codeArea.selectedString.contains("*/"),{
								codeArea.selectedString = codeArea.selectedString.replace("/*","").replace("*/","");
							},{
								codeArea.setString("/*",codeArea.selectionStart,0);
								codeArea.setString("*/",codeArea.selectionStart+codeArea.selectionSize,0);
						});
					}
				);
			});

		});
		codeArea.keyUpAction_({
			OSCthulhu.setSyncArg(bufferName,1,codeArea.string);
			{ bufferLengthText.string_("Chars:" + codeArea.string.size); }.defer;
		});
		/*
		codeArea.receiveDragHandler_({
		OSCthulhu.setSyncArg(bufferName,1,codeArea.string);
		});
		*/

		// close the buffer
		closeBtn = Button(view,20@20);
		closeBtn.states_([
			["X",Color.white,Color.gray(0.35)]
		]);
		closeBtn.action_({|btn|
			OSCthulhu.removeSyncObject(bufferName);
		});

		// run button
		execBtn = Button(view,20@40);

		if(selectedBy == "none",{
			execBtn.states_([["Run",Color.white,Color.red(0.8)]]);
			},{
				execBtn.states_([["Run",Color.gray(0.5),Color.red(0.5)]]);
		});

		if(selectedBy == OSCthulhu.userName.asString,{
			execBtn.states_([["Run",Color.white,Color.red(0.8)]]);
		});

		execBtn.action_({|btn|
			if(selectedBy == "none",{
				OSCthulhu.setSyncArg(bufferName,2,runCount + 1);
			});

			if(selectedBy == OSCthulhu.userName.asString,{
				OSCthulhu.setSyncArg(bufferName,2,runCount + 1);
			});
		});

		// edit button
		editBtn = Button(view,20@40);
		editBtn.states_([
			["Edit",Color.white,Color.black]/*,
			["Release",Color.white,Color.black]*/
		]);

		editBtn.action_({|btn|

			if(selectedBy == "none",{
				OSCthulhu.setSyncArg(bufferName,0,OSCthulhu.userName);
			});

			if(selectedBy == OSCthulhu.userName.asString,{
				OSCthulhu.setSyncArg(bufferName,0,"none");
				OSCthulhu.setSyncArg(bufferName,1,codeArea.string);
			});

		});

		// add to view
		view.layout_(
			VLayout(
				HLayout(
					[bufferLengthText,stretch:16],
					[editBtn, stretch:16],
					[execBtn, stretch:16],
					[closeBtn, stretch:1]
				),
				codeArea
			)
		);

		^view;
	}

	setSelectedBy
	{|name|
		selectedBy = name;

		if(selectedBy == OSCthulhu.userName.asString,{
			{
				codeArea.editable_(true);
				codeArea.stringColor_(Color.white);
				execBtn.states_([["Run",Color.white,Color.red(0.8)]]);
			}.defer;
			{ editBtn.states_([["Release",Color.white,Color.black]]) }.defer;
			},{
				{
					codeArea.editable_(false);
					codeArea.stringColor_(Color.gray(0.75));
					execBtn.states_([["Run",Color.white,Color.red(0.8)]]);
				}.defer;

				if(selectedBy != "none",{
					{
						codeArea.stringColor_(Color.gray(0.75));
						editBtn.states_([[selectedBy,Color.gray(0.75),Color.gray(0.25)]]);
						execBtn.states_([["Run",Color.gray(0.5),Color.red(0.5)]]);
					}.defer;
					},{
						{ editBtn.states_([["Edit",Color.white,Color.black]]); }.defer;
				});
		});
	}

	setCode
	{|newCode|
		if(selectedBy != OSCthulhu.userName.asString,{
			{
				codeArea.string_(newCode);
				bufferLengthText.string_("Chars:" + codeArea.string.size);
			}.defer;
		});
		code = newCode;
	}

	setRunCount
	{|newCount|
		if(runCount != newCount,{
			// codeArea.string.interpret;
			CthulhuPoolPostWindow.update( code.interpret );
			runCount = newCount;
		});
	}

	free
	{

	}
}

CthulhuPoolPostWindow
{
	classvar postWin;

	*getView
	{
		postWin = TextView();
		postWin.editable_(false);

		^postWin;
	}

	*update
	{|post|
		{
			postWin.string = postWin.string ++ "\n" ++ post;
			postWin.select(postWin.string.size-2,0);
		}.defer;
	}
}