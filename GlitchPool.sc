/*
* GlitchPool
* A simple live coding system for use with the OSCthulhu netowrk system.
*
* by Cole D. Ingraham
*
*/

GlitchPool {
	classvar osc; // holds all OSCdefs used by the class
	classvar <users;

	* new {
		users = Dictionary();
		osc = List();
		this.setupOSClisteners;
		OSCthulhu.changePorts([57120,32244]);
		OSCthulhu.login("GP");
	}

	* setupOSClisteners {

		osc.add(
			OSCthulhu.onUserName("setOSCthulhuUserName",{|msg|

				OSCthulhu.userName = msg[1];
				OSCthulhu.addSyncObject(OSCthulhu.userName,"GP","u",["",0]);

				// broadcast interpreted code to all GlitchPool users
				thisProcess.interpreter.codeDump_({|code|
					var runCount;
					// prevent any code using .unixCmd
					if(code.find("unixCmd").isNil,{
						runCount = GlitchPool.users[OSCthulhu.userName.asString].runCount.asInteger;
						GlitchPool.users[OSCthulhu.userName.asString].runCount_((runCount+1).asInteger);
						OSCthulhu.setSyncArg(OSCthulhu.userName,0,code.asString);
						OSCthulhu.setSyncArg(OSCthulhu.userName,1,(runCount+1).asInteger);
						},{
							"GlitchPool will not send code using .unixCmd".warn;
					});
				});

				// broadcast CmdPeriod to all GlitchPool users
				CmdPeriod.add({
					var runCount;
					runCount = GlitchPool.users[OSCthulhu.userName.asString].runCount.asInteger;
					GlitchPool.users[OSCthulhu.userName.asString].runCount_((runCount+1).asInteger);
					OSCthulhu.setSyncArg(OSCthulhu.userName,0,"CmdPeriod.run;");
					OSCthulhu.setSyncArg(OSCthulhu.userName,1,(runCount+1).asInteger);
				});
			})
		);

		osc.add(
			OSCthulhu.onAddSyncObject("addObject",{|msg|
				var oscAddr, objName, group, subGroup, args;

				# oscAddr, objName, group, subGroup = msg;
				args = msg.copyRange(4,msg.size-1);

				if(group.asString == "GP",{

					if(subGroup.asString == "u",{
						users.put(
							objName.asString,
							GlitchPoolUser(objName.asString,args[0].asString,args[1].asInteger)
						);
					});

				});

			})
		);

		osc.add(
			OSCthulhu.onSetSyncArg("setSyncArg",{|msg, time, addr, recvPort|
				var oscAddr, objName, argIndex, argValue, group, subGroup;
				var runCount;
				# oscAddr, objName, argIndex, argValue, group, subGroup = msg;

				// check if this is for GlitchPool and if the message is coming from OSCthulhu
				if(group.asString == "GP" && addr.port == OSCthulhu.clientPort,{

					if(subGroup.asString == "u",{
						switch(
							argIndex,
							0, {
								// prevent any code using .unixCmd
								if(argValue.asString.find("unixCmd").isNil,{
									GlitchPool.users[objName.asString].code = argValue.asString;
								});
							},
							1, {
								runCount = GlitchPool.users[objName.asString].runCount.asInteger;

								if(argValue.asInteger > runCount,{
									"\n<%>: ".format(objName.asString).post;
									GlitchPool.users[objName.asString].code.asString.postln;
									"".postln;
									GlitchPool.users[objName.asString].code.asString.interpret;
								});

								GlitchPool.users[objName.asString].runCount = argValue.asInteger;
							}
						);
					});

				});
			});
		);

		osc.collect({|def| def.permanent_(true); });
	}

	* free {
		osc.collect({|def| def.permanent_(false); def.free; });
		OSCthulhu.cleanup("GP");
		thisProcess.interpreter.codeDump = nil;
		CmdPeriod.removeAll;
	}

}

GlitchPoolUser {
	var <userName;
	var <>code;
	var <>runCount;

	* new {|userName="",code="",runCount=0|
		^super.newCopyArgs(userName,code,runCount);
	}

}
