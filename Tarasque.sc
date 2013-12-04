
Tarasque : ManticorePiece
{
	var osc;	// hold all OSCdefs
	var desktopView, objectView;

	classvar <scoreIndex, <scoreObjects, <rhythmObjectList;
	var scoreText, scoreEffects;

	*new
	{
		^super.new().init();
	}

	*initClass
	{
		Manticore.registerPiece('Tarasque');	// register Tarasque with Manticore
	}

	init
	{
		Manticore.group = "Trsq";

		osc = List.new;
		scoreIndex = 0;

		rhythmObjectList = [
			[
				"r_Clicker"
			],
			[
				"r_Clicker"
			],
			[
				"r_Clicker"
			],
			[
				"r_QuadC",
				"r_WhiteBurst",
				"r_BabyRats",
				"r_DonkeyLinCong",
				"r_RoboSpeaks",
				"r_FincoSprottL",
				"r_RosslerL",
				"r_AtariBlast",
				"r_LatoocarfianBlast"
			],
			[
				"r_QuadC",
				"r_WhiteBurst",
				"r_BabyRats",
				"r_DonkeyLinCong",
				"r_RoboSpeaks",
				"r_FincoSprottL",
				"r_RosslerL",
				"r_AtariBlast",
				"r_LatoocarfianBlast"
			],
			[
				"r_SawSwarm",
				"r_MetalPercussion",
				"r_ModNoise",
				"r_ChaosMarimba"
			],
			[
				"r_Beeeeeep",
				"r_AtariBlast",
				"r_Henonator",
				"r_RoboSpeaks",
				"r_GbmanFlash",
				"r_FincoSprottL",
				"r_ResKick",
				"r_LatoocarfianBlast"
			],
			[
				"r_Henonator",
				"r_RoboSpeaks",
				"r_GbmanFlash",
				"r_FincoSprottL",
				"r_ResKick"
			],
			[
				"r_DonkeyLinCong",
				"r_QuadC",
				"r_BabyRats",
				"r_WhiteBurst",
				"r_Beeeeeep",
				"r_AtariBlast",
				"r_LatoocarfianBlast",
				"r_RoboSpeaks",
				"r_SawSwarm",
				"r_MetalPercussion",
				"r_ModNoise",
				"r_ChaosMarimba",
				"r_ChaosGeometry.low",
				"r_ChaosGeometry.mid",
				"r_ChaosGeometry.high",
				"r_Henonator",
				"r_GbmanFlash",
				"r_RosslerL",
				"r_FincoSprottL",
				"r_ResKick"
			]
		];

		scoreText = [
			//			"For testing only.",

			"\"Dusty Combo\"\n\n"
			"add a DustyCombo\n"
			"add Clickers to that\n\n"
			"MOVE ON QUICKLY!",

			"\"Feedback 1\"\n\n"
			"add a pp zone to add all feedback synths to.\n"
			"slowly add x4 PhaseModFB.\n"
			"slowly add x2 PlatapusFB.",

			"\"Feedback 2\"\n\n"
			"slowly add x2 ChirpSpiralFB.\n"
			"add a p zone to an empty area.\n"
			"slowly exchange the p for the pp and back.",

			"\"Grooves I.1\"\n\n"
			"swap Dusty with SteadyBeat III\n"
			"add GrooveCombo and blues to that\n"
			"move SteadyBeat III to GrooveCombo and kill DustyCombo\n"
			"kill old purples and add Recursion",

			"\"Grooves I.2\"\n\n"
			"exchange SteadyBeat III with IV\n"
			"add Dispersion\n"
			"*** pressing R with a blue object selected randomizes it ***",

			"\"RU Groove\"\n\n"
			"add RUPulse and RUBright.\n"
			"exchange SteadyBeat IV with V\n"
			"kill Recursion",

			"\"FP Groove\"\n\n"
			"add a few ChaosGeometry mid and high\n"
			"kill all RUPulse, RUBright and purples\n"
			"slowly add a few Comber",

			"\"OakStyle Groove\"\n\n"
			"START IT PP!!!\n"
			"add OakFinale\n"
			"crescendo OakStyle with dynamic zones\n"
			"kill Comber",

			"\"Ending\"\n\n"
			"slowly add a few in order:\n"
			"Eviceration, Dispersion, Comber, PlatapusFB, PhaseModFB\n"
			"to end:\n"
			"kill all non-purples\n"
			"kill purples one at a time, ending with one PhaseModFB"

		];

		scoreObjects = [
			/*
			[	// [0] Everything for testing
			"m_BasicGrooveCombo",
			"c_SteadyBeat.I",
			"c_SteadyBeat.II",
			"c_SteadyBeat.III",
			"c_SteadyBeat.IV",
			"c_SteadyBeat.V",
			"c_SteadyBeat.VI",
			"c_SteadyBeat.VII",
			"c_Dusty",
			"p_WholeNoteWalk",
			"p_Constant.whole",
			"p_Constant.half",
			"p_Constant.quarter",
			"p_Constant.offBeat",
			"p_Constant.eighth",
			"p_1of3.whole",
			"p_1of3.half",
			"p_1of3.quarter",
			//"p_1of3.offBeat",
			//"p_1of3.eighth",
			"p_2of3.whole",
			"p_2of3.half",
			"p_2of3.quarter",
			//"p_2of3.offBeat",
			"p_2of3.eighth",
			"p_NineTrip",
			"p_Pentambeter",
			"p_FiveShuffle",
			"p_IceCreamPig",
			"p_WoodenOctopus",
			"r_Clicker",
			"r_DonkeyLinCong",
			"r_QuadC",
			"r_BabyRats",
			"r_WhiteBurst",
			"r_Beeeeeep",
			"r_AtariBlast",
			"r_LatoocarfianBlast",
			"r_RoboSpeaks",
			"r_SawSwarm",
			"r_MetalPercussion",
			"r_ModNoise",
			"r_ChaosMarimba",
			"r_ChaosGeometry.low",
			"r_ChaosGeometry.mid",
			"r_ChaosGeometry.high",
			"r_Henonator",
			"r_GbmanFlash",
			"r_RosslerL",
			"r_FincoSprottL",
			"r_ResKick",
			"r_OakStyle.one",
			"r_OakStyle.two",
			"r_OakStyle.four",
			"r_RUPulse",
			"r_RUBright.1:1",
			"r_RUBright.9:8",
			"r_RUBright.8:7",
			"d_DeadAnimalJuice",
			//				"r_FPJam.1:1",
			//				"r_FPJam.10:9",
			//				"r_FPJam.9:8",
			//				"r_FPJam.8:7",
			//				"r_FPJam.7:6",
			//				"r_FPJam.13:11",
			//				"r_FPJam.6:5",
			//				"r_FPJam.5:4",
			//				"r_FPJam.4:3",
			//				"r_FPJam.3:2",
			//				"r_FPJam.5:3",
			//				"r_FPJam.7:4",
			//				"r_FPJam.16:9",
			//				"r_FPJam.9:5",
			"d_ChirpSpiralFB",
			"d_PhaseModFB",
			"d_PlatapusFB",
			"d_DelayFB"
			],
			*/
			[	// Dusty Combo
				"m_DustyCombo",
				"c_Dusty",
				"p_NineTrip",
				"p_Pentambeter",
				"p_FiveShuffle",
				"r_Clicker"
			],
			[	// FB Section 1
				"d_PhaseModFB",
				"d_PlatapusFB"
			],
			[	// FB Section 2
				"d_ChirpSpiralFB",
				"d_PhaseModFB",
				"d_PlatapusFB"
			],
			[	// Grooves I.1
				"m_GrooveCombo",
				"c_SteadyBeat.III",
				"p_Pentambeter",
				"p_NineTrip",
				"r_QuadC",
				"r_WhiteBurst",
				"r_BabyRats",
				"r_DonkeyLinCong",
				"r_RoboSpeaks",
				"r_FincoSprottL",
				"r_RosslerL",
				"r_AtariBlast",
				"r_LatoocarfianBlast",
				"r_ChaosGeometry.low",
				"d_Recursion"
			],
			[	// Grooves I.2
				"c_SteadyBeat.IV",
				"p_WholeNoteWalk",
				"r_QuadC",
				"r_WhiteBurst",
				"r_BabyRats",
				"r_DonkeyLinCong",
				"r_RoboSpeaks",
				"r_FincoSprottL",
				"r_RosslerL",
				"r_AtariBlast",
				"r_LatoocarfianBlast",
				"r_ChaosGeometry.low",
				"d_Dispersion",
				"d_Recursion"
			],
			[	// RU Groove
				"c_SteadyBeat.V",
				"r_RUPulse",
				"r_RUBright.1:1",
				"r_RUBright.9:8",
				"r_RUBright.8:7",
				"r_SawSwarm",
				"r_MetalPercussion",
				"r_ModNoise",
				"r_ChaosMarimba",
				"d_DelayFB",
			],
			[	// Grooves II.1
				"m_GrooveComboIII",
				"c_SteadyBeat.V",
				"p_WholeNoteWalk",
				"p_Pentambeter",
				"p_NineTrip",
				"p_FiveShuffle",
				"r_Beeeeeep",
				"r_AtariBlast",
				"r_Henonator",
				"r_RoboSpeaks",
				"r_GbmanFlash",
				"r_FincoSprottL",
				"r_ResKick",
				"r_LatoocarfianBlast",
				"r_ChaosGeometry.low",
				"r_ChaosGeometry.mid",
				"r_ChaosGeometry.high",
				"r_FPJam.bass",
				"r_FPJam.9:8",
				"r_FPJam.8:7",
				"r_FPJam.7:6",
				"r_FPJam.13:11",
				"d_PinkSpagetti",
				"d_Comber"
			],
			[	// OakStyle 1
				// "m_OakStyleCombo",
				// "c_OakFinale",
				"c_SteadyBeat.IV",
				"c_SteadyBeat.V",
				"p_WholeNoteWalk",
				"p_Pentambeter",
				"p_NineTrip",
				"p_FiveShuffle",
				"r_Henonator",
				"r_RoboSpeaks",
				"r_GbmanFlash",
				"r_FincoSprottL",
				"r_ResKick",
				"d_OakStyle.one",
				"d_OakStyle.two",
				"d_OakStyle.four"
			],
			[	// end
				"m_GrooveCombo",
				"c_SteadyBeat.VI",
				"c_SteadyBeat.VII",
				"r_DonkeyLinCong",
				"r_QuadC",
				"r_BabyRats",
				"r_WhiteBurst",
				"r_Beeeeeep",
				"r_AtariBlast",
				"r_LatoocarfianBlast",
				"r_RoboSpeaks",
				"r_SawSwarm",
				"r_MetalPercussion",
				"r_ModNoise",
				"r_ChaosMarimba",
				"r_ChaosGeometry.low",
				"r_ChaosGeometry.mid",
				"r_ChaosGeometry.high",
				"r_Henonator",
				"r_GbmanFlash",
				"r_RosslerL",
				"r_FincoSprottL",
				"r_ResKick",
				"d_ChirpSpiralFB",
				"d_PhaseModFB",
				"d_PlatapusFB",
				"d_Comber",
				"d_Dispersion",
				"d_Evisceration"
			]
		];

		scoreEffects = [
			//			"CrunchVerb.gain3",				// [0]
			"CrunchVerb.gain1",				// [Dusty]
			"CrunchVerb.gain3",				// [FB1]
			"CrunchVerb.gain6",				// [FB2]
			"CrunchVerb.gain1",				// [Grooves I.1]
			"CrunchVerb.gain2",				// [Grooves I.2]
			"CrunchVerb.gain1",				// [RU Groove]
			"CrunchVerb.gain3",				// [Grooves II]
			"DifferenceToneAmp.gain2",			// [OakStyle]
			"DifferenceToneAmp.gain5"			// [End]
		];

		this.setupOSClisteners;
		this.storeSynthDefs;

		OSCthulhu.changePorts(57120);
		OSCthulhu.login(Manticore.group);

		// make the score buttons work
		Manticore.onScoreNext({
			OSCthulhu.setSyncArg("Trsq_Global", 0, (scoreIndex+1)%scoreText.size);
		});
		Manticore.onScorePrevious({
			OSCthulhu.setSyncArg("Trsq_Global", 0, (scoreIndex-1)%scoreText.size);
		});

		// args: scoreIndex, volume, distortionState, distortionVal, reverbState, reverbVal
		OSCthulhu.addSyncObject(
			"Trsq_Global",
			Manticore.group,
			"glbl",
			[
				0,		// scoreIndex
				1.0		// volume
			]
		);
	}

	setupOSClisteners
	{
		// addSyncObject
		osc.add(OSCthulhu.onAddSyncObject(\addScore, {|msg|
			var oscAddr, objName, group, subGroup, args;

			# oscAddr, objName, group, subGroup = msg;
			args = msg.copyRange(4,msg.size-1);

			if(subGroup.asString == "glbl",{
				scoreIndex = args[0];
				Manticore.updateScore(scoreText[scoreIndex]);
			});

			})
		);

		// setSyncArg
		osc.add(OSCthulhu.onSetSyncArg(\updateScore, {|msg|
			var oscAddr, objName, argIndex, argValue, group, subGroup;

			# oscAddr, objName, argIndex, argValue, group, subGroup = msg;

			if(subGroup.asString == "glbl", {
				switch(
					argIndex,
					0, {

						if(argValue != scoreIndex,{
							TarasqueServerItems.changeEffects(scoreEffects[argValue].asString);
						});

						scoreIndex = argValue;
						Manticore.updateScore(scoreText[scoreIndex]);
					}
				)
			});
			})
		);
	}

	free
	{
		desktopView.free;
		objectView.free;
		osc.collect({|def| def.free; });
		OSCthulhu.cleanup(Manticore.group);
		CmdPeriod.removeAll;
	}

	getInterface
	{|p|
		desktopView = TarasqueDesktop.new;
		objectView = TarasqueObjectWindow.new;

		^p.layout_(HLayout( desktopView.getView, objectView.getView ));
	}

	storeSynthDefs
	{
		// colotomy
		(
			SynthDef("SteadyBeat",{|out = 100, rate=4.5|
				var whole, half, quarter, offBeat, eighth;

				whole = Impulse.ar(rate*0.25);
				half = Impulse.ar(rate*0.5);
				quarter = Impulse.ar(rate);
				offBeat = Impulse.ar(rate,0.5);
				eighth = Impulse.ar(rate*2);

				Out.ar(out,[whole, half, quarter, offBeat, eighth]);
				},
				variants: (
					I: [ rate: 2.8571428571429 ],
					II: [ rate: 3.5714285714286 ],
					III: [ rate: 5 ],
					IV: [ rate: 5.7142857142857 ],
					V: [ rate: 7.8571428571429 ],
					VI: [ rate: 9.2857142857143 ],
					VII: [ rate: 11.428571428571 ],
				)
			).store;
		);

		(
			SynthDef("Dusty",{|out = 100|
				var rate, whole, half, quarter, offBeat, eighth;

				rate = 4;

				whole = Dust.ar(rate*0.3);
				half = Dust.ar(rate*0.6);
				quarter = Dust.ar(rate);
				offBeat = Dust.ar(rate,0.5);
				eighth = Dust.ar(rate*2);

				Out.ar(out,[whole, half, quarter, offBeat, eighth]);
			}).store;
		);

		(
			SynthDef("OakRamp",{|out = 100|
				var rate, whole, half, quarter, offBeat, eighth;

				//rate = EnvGen.kr(Env.new([0.5,50,1000], [60*9.75,60*4.25], [\exp,\lin]), 1);
				rate = EnvGen.kr(Env.new([0.5,50,1000], [60*2.75,60*1.25], [\exp,\lin]), 1);

				whole = Impulse.ar(rate*0.3);
				half = Impulse.ar(rate*0.6);
				quarter = Impulse.ar(rate);
				offBeat = Impulse.ar(rate,0.5);
				eighth = Impulse.ar(rate*2);

				Out.ar(out,[whole, half, quarter, offBeat, eighth]);
			}).store;
		);

		(
			SynthDef("OakFinale",{|out = 100|
				var rate, whole, half, quarter, offBeat, eighth;

				//rate = EnvGen.kr(Env.new([0.5,50,1000], [60*9.75,60*4.25], [\exp,\lin]), 1);
				rate = LFNoise2.kr(LFNoise0.kr(1).range(0.2,2)).range(800,1200);
				whole = Impulse.ar(rate*0.3);
				half = Impulse.ar(rate*0.6);
				quarter = Impulse.ar(rate);
				offBeat = Impulse.ar(rate,0.5);
				eighth = Impulse.ar(rate*2);

				Out.ar(out,[whole, half, quarter, offBeat, eighth]);
			}).store;
		);

		// patterns
		(
			SynthDef("Constant",{|in = 100, out = 100, index=0|
				var input, sig;

				input = In.ar(in+index,1);

				sig = input;

				Out.ar(out, sig);
				},
				variants: (
					whole: [ index: 0 ],
					half: [ index: 1 ],
					quarter: [ index: 2 ],
					offBeat: [ index: 3 ],
					eighth: [ index: 4 ]
			)).store;
		);

		(
			SynthDef("NineTrip",{|in = 100, out = 100|
				var input, sig, seq, trig;

				input = In.ar(in,5);

				seq = Dseq([1,1,0,1,0,1,1,1,0],inf);

				trig = Select.ar(LFSaw.kr(0.3).range(0,5).floor, input);

				sig = trig * Demand.ar(trig, 0, seq);

				Out.ar(out, sig);
			}).store;
		);

		(
			SynthDef("Pentambeter",{|in = 100, out = 100|
				var input, sig, seq, trig;

				input = In.ar(in,5);

				seq = Dseq([1,0,1,1,0],inf);

				trig = Select.ar(LFSaw.kr(0.3).range(0,3).floor, [input[0],input[2],input[4]]);

				sig = trig * Demand.ar(trig, 0, seq);

				Out.ar(out, sig);
			}).store;
		);

		(
			SynthDef("1of3",{|in = 100, out = 100, index=0|
				var input, sig;

				input = In.ar(in+index,1);

				sig = PulseDivider.ar(input, 3);

				Out.ar(out, sig);
				},
				variants: (
					whole: [ index: 0 ],
					half: [ index: 1 ],
					quarter: [ index: 2 ],
					offBeat: [ index: 3 ],
					eighth: [ index: 4 ]
			)).store;
		);

		(
			SynthDef("2of3",{|in = 100, out = 100, index=0|
				var input, sig, seq;

				input = In.ar(in+index,1);

				seq = Dseq([1,1,0],inf);

				sig = Demand.ar(input, 0, seq);

				sig = input * sig;

				Out.ar(out, sig);
				},
				variants: (
					whole: [ index: 0 ],
					half: [ index: 1 ],
					quarter: [ index: 2 ],
					offBeat: [ index: 3 ],
					eighth: [ index: 4 ]
			)).store;
		);

		(
			SynthDef("WholeNoteWalk",{|in = 100, out = 100|
				var input, sig, seq;

				input = In.ar(in,5);

				seq = Dseq([1,0,1,0,0,1,0],inf);

				sig = input[0] * Demand.ar(input[0], 0, seq);

				Out.ar(out, sig);
			}).store;
		);

		(
			SynthDef("FiveShuffle",{|in = 100, out = 100|
				var input, sig, seq;

				input = In.ar(in,5);

				input = Select.ar(TChoose.kr(Dust.kr(Rand(0.2,1)),[2,3,5]), input);

				seq = Dfsm(
					[
						[1],
						Dshuf([1,0,0,1,1]), [0,0,0,1,1],
						Dshuf([0,1,1,0,0]), [0,0,1,1,1]
					],
					inf
				);

				sig = Demand.ar(input, 0, seq);

				sig = input * sig;

				Out.ar(out, sig);
			}).store;
		);

		(
			SynthDef("IceCreamPig",{|in = 100, out = 100|
				var input, sig, seq;

				input = In.ar(in,5);

				input = input[2];

				seq = Dfsm(
					[
						[1],
						Dseq([1,0,0,0,0,0,0,1,1,0,0]), [0,0,0,1,1],
						Dseq([1,0,0,1,1]), [0,0,1,1,1,2],
						Dseq([1,0,0,1,0,1,0]), [1,1,2,2,2,2,2]
					],
					inf
				);

				sig = Demand.ar(input, 0, seq);

				sig = input * sig;

				Out.ar(out, sig);
			}).store;
		);

		(
			SynthDef("WoodenOctopus",{|in = 100, out = 100|
				var input, sig, seq;

				input = In.ar(in,5);

				input = input[1];

				seq = Dfsm(
					[
						[1],
						Dseq([1,0,0,0,1,0,1,0,0,0,0]), [0,0,0,1,1],
						Dseq([1,0,1,0,1,1,0,0,1]), [0,1,2],
						Dseq([1,0,0,1,0,1,0]), [0,1,1,2,2,2,2,2]
					],
					inf
				);

				sig = Demand.ar(input, 0, seq);

				sig = input * sig;

				Out.ar(out, sig);
			}).store;
		);

		// drums

		this.rhythmSynthWrap("Clicker",{|argA,argB,trig|
			var sig;

			sig = Decay.ar(
				DelayN.ar(trig,0.001,0.001),
				TRand.ar(0.0001,0.002,trig)
			);
		});

		this.rhythmSynthWrap("MetalPercussion",{|argA,argB,trig|
			var sig, type=0, den, freq, bufA, bufC;

			freq = TRand.ar(10,100,trig);
			den = TRand.ar(300,800,trig);

			bufC = LocalBuf.new(SampleRate.ir * 0.01, 1);

			sig = BufCombC.ar(
				bufC,
				HPF.ar(
					PinkNoise.ar(
						Decay2.ar(
							Impulse.ar(den,0,LFNoise1.kr(300))
							,0.05,0.2)
					)
					,LFNoise1.kr(10).range(20,500) * TRand.ar(1,10,trig)),
				1/(
					SinOsc.kr(40).range(freq,freq*1.5)+LFNoise1.kr(1,freq/4).round(10)
				)
				,3);

			bufA = LocalBuf.new(SampleRate.ir * 0.01, 1);

			sig = BufAllpassC.ar(
				bufA,
				Ringz.ar(
					sig, {ExpRand(100, 6000)}.dup(100),{ExpRand(1, 2)}.dup(100)
				).mean
				,0.01,4,1.3);

			sig = LPF.ar(sig, Rand(1000,3000)) * 0.34;
		});

		this.rhythmSynthWrap("WhiteBurst",{|argA,argB,trig|
			var f, r, sig;

			f = TRand.ar(100.0,7000.0,trig);
			r = TRand.ar(0.025,3,trig);

			sig = Resonz.ar(
				WhiteNoise.ar(TRand.ar(1,4,trig)),
				f,
				r
			);

		});

		this.rhythmSynthWrap("GbmanFlash",{|argA,argB,trig|
			(GbmanL.ar(TRand.ar(200, 800,trig))).softclip * 1.8;
		});

		this.rhythmSynthWrap("Henonator",{|argA,argB,trig|
			HenonC.ar(
				LFNoise2.kr(
					LFNoise0.kr(2).range(0.5,3)
				).range(
					TRand.ar(100, 4000, trig),
					TRand.ar(100, 4000, trig)
				)
			);
		});

		this.rhythmSynthWrap("LatoocarfianBlast",{|argA,argB,trig|
			(
				(
					LatoocarfianC.ar(
						Rand(10, SampleRate.ir/10) * TRand.ar(2,20,trig)
					)*TRand.ar(1,3,trig)
				).distort * TRand.ar(2,20,trig)
			).softclip;
		});

		this.rhythmSynthWrap("DonkeyLinCong",{|argA,argB,trig|
			Resonz.ar(
				LinCongC.ar(Rand(50, SampleRate.ir/10) * TRand.ar(1,10,trig)),
				Rand(100,1000),
				TRand.ar(0.0005,5,trig)
			) * TRand.ar(1,7);
		});

		this.rhythmSynthWrap("SawSwarm",{|argA,argB,trig|
			var sig, center, centLag = 0.5, deviation, deviationHz;

			center = TRand.kr(5,500,trig);
			deviation = TRand.kr(center/3,center,trig);
			deviationHz = TRand.kr(1,15,trig);

			sig = Mix.new(
				Pulse.ar(
					LFNoise2.ar(deviationHz.dup(30), deviation.dup(30),  center),
					Rand(0.2,0.8),
					0.05
				)
			);

			sig = LPF.ar(
				sig,
				Lag.kr(LFNoise2.kr(2,deviation,center*13),centLag),
				TRand.ar(1,5,trig)
			).distort * 1.3;
		});

		this.rhythmSynthWrap("ResKick",{|argA,argB,trig|
			var sig, freq;

			freq = TRand.ar(3,70,trig);

			sig = Resonz.ar(
				SinOsc.ar(
					LFSaw.kr(
						LFNoise1.ar(
							1, 200, 250
						),
						0,freq,freq+150
					),
					0,0.3
				),
				SinOsc.kr(
					TRand.ar(50,150,trig)
				),
				10
			);

			sig = sig.fold2.(0.5) * 0.62;

			sig = Decay2.ar(sig,0.005,0.1,0.666);
		});

		this.rhythmSynthWrap("ModNoise",{|argA,argB,trig|
			var sig, freq, amfreq, phasemod, filterModFreq, buf;

			freq = TRand.ar(50,600,trig);
			amfreq = TRand.ar(0,100,trig);
			phasemod = TRand.ar(0,1000,trig);
			filterModFreq = TRand.ar(0,100,trig);

			sig = LPF.ar(
				SinOsc.ar(
					freq,SinOsc.kr(
						phasemod,0,2pi
					),
					1
				),
				SinOsc.kr(
					filterModFreq,0,1400,1600
				),
				SinOsc.kr(
					amfreq,0,0.15,0.15
				)
			);

			buf = LocalBuf.new(SampleRate.ir * 0.01, 1);

			sig = LPF.ar(
				BufAllpassC.ar(
					buf,
					Ringz.ar(
						sig,
						{exprand(10, 2000)}.dup(10)
						,{ExpRand(0.2, 2)}.dup(10)).mean,0.01,4,1.3),
				1000) * 0.34;
		});

		this.rhythmSynthWrap("ChaosMarimba",{|argA,argB,trig|
			var sig, freq, buf;

			freq = TRand.ar(5,800,trig);

			sig = Resonz.ar(
				Ringz.ar(
					Impulse.ar(
						LFNoise2.kr(
							Rand(0.5,2)
						).range(10,20),
						0,1
					),
					freq, 3
				),
				220, TRand.ar(0.005,0.25,trig)
			);

			buf = LocalBuf.new(SampleRate.ir * 0.01, 1);

			sig = BufAllpassC.ar(
				buf,
				Ringz.ar(
					sig*0.5,
					{exprand(100, 20000)}.dup(10)
					,{exprand(1, 4)}.dup(10)).mean,0.01,4,1.3);

			sig = Decay2.ar(sig,0.005,0.1,0.33).fold2.(0.5);
		});

		this.rhythmSynthWrap("AtariBlast",{|argA,argB,trig|
			Atari2600.ar(
				LFNoise0.kr(TRand.ar(5,70,trig)).range(0,15).round,
				LFNoise0.kr(TRand.ar(5,70,trig)).range(0,15).round,
				LFNoise0.kr(TRand.ar(5,70,trig)).range(0,31).round,
				LFNoise0.kr(TRand.ar(5,70,trig)).range(0,31).round,
				LFNoise0.kr(TRand.ar(5,70,trig)).range(0,15).round,
				LFNoise0.kr(TRand.ar(5,70,trig)).range(0,15).round
			) * 1.6;
		});

		this.rhythmSynthWrap("Beeeeeep",{|argA,argB,trig|
			Clip.ar(
				(Mix(
					Beep.ar(
						LFNoise1.kr(TRand.ar(0.5,15,trig)).range(TRand.ar(0.1,50,trig),TRand.ar(0.1,50,trig)),
						LFPulse.kr(TRand.ar(2.5,50))
					)
					).dup(10)
			), -1, 1) * 1.3;
		});

		this.rhythmSynthWrap("RoboSpeaks",{|argA,argB,trig|
			Nes2Noise.ar(
				Dust.kr(30),
				0,
				0,
				LFNoise0.kr(TRand.ar(1.5,30,trig), 3.5, 3.5),
				LFNoise0.kr(TRand.ar(1.5,30,trig), 0.5, 0.5),
				LFNoise0.kr(TRand.ar(1.5,30,trig), 3, 4),
				LFNoise0.kr(TRand.ar(1.5,30,trig), 15.5, 15.5)
			).dup * 1.6;
		});

		this.rhythmSynthWrap("FincoSprottL",{|argA,argB,trig|
			(
				FincoSprottL.ar(
					Rand(500,SampleRate.ir/10) * TRand.ar(1,10,trig)
				) * TRand.ar(0.4,2,trig)
			).softclip * 1.3;
		});

		this.rhythmSynthWrap("QuadC",{|argA,argB,trig|
			var r;

			r = TRand.ar(3.5441,4,trig);

			QuadC.ar(SampleRate.ir/4, r.neg, r, 0, 0.1) * 1.6;
		});

		this.rhythmSynthWrap("RosslerL",{|argA,argB,trig|
			RosslerL.ar(
				Rand(200, SampleRate.ir),
				TRand.ar(0.1,0.36,trig),
				TRand.ar(0.1,0.36,trig),
				4.5
			) * 0.62;
		});

		this.rhythmSynthWrap("BabyRats",{|argA,argB,trig|
			(
				StandardL.ar(
					Rand(2, SampleRate.ir/10) * TRand.ar(1,10,trig)
				) * TRand.ar(0.5,5,trig)
			).distort * 1.3;
		});

		this.rhythmSynthWrap("ChaosGeometry",{|argA,argB,trig, hiA=3000, loA=10, hiB=20, loB=1|
			var sig;

			sig = DFM1.ar(
				Silent.ar(1),
				Rand(loA,hiA) + TRand.ar(-10,20,trig),
				Rand(loB,hiB) + TRand.ar(-1,2,trig),
				type: 1,
				noiselevel:0.0015,
				mul:1
			);

			sig = Mix.ar(
				DelayC.ar(sig, 0.1, LFTri.kr(LFNoise2.kr(0.2).range(0.4,1), [0,0.5pi], 0.0024, 0.0025), 1, sig)
			);

			sig = sig.fold2(0.7);

			},
			(
				"low": [ loA: 20, hiA: 30, loB: 2, hiB: 5 ],
				"mid": [ loA: 500, hiA: 700, loB: 9, hiB: 12 ],
				"high": [ loA: 2000, hiA: 3000, loB: 17, hiB: 20 ]
			),
			decayTime: 0.13);

		// pitched dums
		// TODO: make a wrapper for OakStyle
		(
			SynthDef("OakStyle",{|amp=1.0, fund = 440, out=100, in=100, gate=1, vol=1|
				var sig, input, dist, gateing, a, b, f, n = inf, pulseRate, trig, env, buf1, buf2;

				input = Impulse.ar(LFNoise2.kr(LFNoise0.kr(1).range(0.2,2)).range(800,1200));//In.ar(in,1);

				trig = input;

				pulseRate = Timer.ar(trig);
				pulseRate.linlin(1,1/2000,0.5,1000);

				vol = Lag.kr(vol,0.5);

				a = Dfsm([
					[0],
					Dshuf([1, 8/7, 9/8, 10/9, 2], inf), [0, 0, 0, 1, 1, 2, 2, 3],
					Dshuf([1, 3/2, 4/3, 9/8, 2], inf), [0, 0, 1, 1, 1],
					Dshuf([1, 6/5, 7/6, 5/4, 2], inf) , [0, 0, 3, 3],
					Dshuf([1, 7/4, 16/9, 2], inf) , [0, 0, 3, 3, 3, 3]
				], n);
				b = Demand.ar(trig, 0, a);

				gateing = EnvGen.ar(Env.perc(0.001,1,1,'linear'),input);

				f = b * fund;

				buf1 = LocalBuf.new(SampleRate.ir, 1);

				sig = BufCombC.ar(buf1, Impulse.ar(f / TIRand.kr(1,5,trig)), 1/f, 4);

				buf2 = LocalBuf.new(SampleRate.ir, 1);

				sig = sig + BufCombC.ar(buf2, Impulse.ar(f / TIRand.kr(1,5,trig)), 1/fund, 4);

				sig = sig * gateing * (Lag.ar(b,0.01)*0.05);

				sig = RLPF.ar(
					sig,
					f*LFNoise1.kr(
						LFNoise0.kr(3).range(pulseRate/2,pulseRate*2)).range(1,7),
					LFNoise1.kr(LFNoise0.kr(2).range(0.2,2)
					).range(1,0.05)
				);

				dist = HPF.ar(LPF.ar((sig*80).distort,fund/4,3).distort,fund/6);

				env = EnvGen.kr(Env.asr(0.01, 0.1, 2), gate, doneAction:2);

				sig = sig + LPF.ar(sig,110,24).distort;

				sig = LeakDC.ar((sig+dist).distort * amp * 32);

				sig = sig.distort;

				Out.ar(out,sig*env*gateing*vol);
				},
				variants: (
					one: [ fund: 110 ],
					two: [ fund: 220 ],
					four: [ fund: 440 ]
			)).store
		);


		this.rhythmSynthWrap("RUPulse",{|argA,argB,trig,freq = 55|
			var sig, env, lfo, seq, demand;

			lfo = LFNoise2.kr(LFNoise0.kr(Rand(0.5,1)).range(0.1,0.3)).range(freq*2,freq*20);

			seq = Dshuf([
				Dshuf([ 1, 1, 6/5 ],IRand(1,4)),
				Dshuf([ 1, 1, 1, 1, 9/8 ],IRand(1,4)),
				Dshuf([ 1, 1, 1, 7/8 ],IRand(1,4))
				],
				inf
			);

			demand = Demand.ar(trig, 0, seq);

			sig = Pulse.ar([freq,freq*2,freq*1.0021,freq*2.0013] * demand, TRand.ar(0.02,0.5,trig));

			sig = Mix.ar(sig);

			sig = MoogLadder.ar(
				sig,
				lfo,
				TRand.ar(0.01,0.99,trig)
			) * 2;

			sig = LeakDC.ar(sig);

		},decayTime: 0.13);

		this.rhythmSynthWrap("RUBright",{|argA,argB,trig,freq=440|
			var sig, env, seq, demand;

			seq = Dshuf([
				Dshuf([ 1, 1, 1, 5/3 ],IRand(1,4)),
				Dshuf([ 1, 1, 1, 1, 7/4],IRand(1,4)),
				Dshuf([ 1, 1, 16/9 ],IRand(1,4))
				],
				inf
			) * 1.3;

			demand = Demand.ar(trig, 0, seq * freq);

			sig = Mix.ar(Blip.ar(demand * ({Rand(0.985,1.015)}.dup(10)), TRand.ar(3,13,trig) ));

			sig = MoogLadder.ar(
				sig,
				demand * TRand.ar(3,7,trig),
				TRand.ar(0.0,1.0,trig)
			) * 2;

			sig = LeakDC.ar(sig);

			},
			(
				"1:1": [ freq: 440 ],
				"9:8": [ freq: 440 * 9/8 ],
				"8:7": [ freq: 440 * 8/7 ]
			),
			decayTime: 0.13
		);

		this.rhythmSynthWrap("FPJam",{|argA,argB,trig,freq=440|
			var sig, lfo, seq, demand;

			seq = Dshuf([
				Dshuf([ 1, 1, 1, 2 ],IRand(1,5)),
				Dshuf([ 1, 1, 2, 1, 0.5],IRand(1,5)),
				Dshuf([ 1, 1, 0.5 ],IRand(1,5))
				],
				inf
			) * 1.3;

			demand = Demand.ar(trig, 0, seq);

			freq = freq * demand;

			sig = Mix.new(Saw.ar([freq*0.51,freq,freq*2.01],5)).distort;

			sig = MoogLadder.ar(
				sig,
				freq * argA.linlin(0.0,1.0,2,7),
				argB.linlin(0.0,1.0,0.08,1)
			);

			lfo = SinOsc.kr(Rand(0.5,2)).range(0.25,2.0);

			sig = HPF.ar(sig,freq*lfo);

			sig = LeakDC.ar(sig * 10).distort;
			},(
				"1:1": [ freq: 440 ],
				"10:9": [ freq: 440 * 10/9 ],
				"9:8": [ freq: 440 * 9/8 ],
				"8:7": [ freq: 440 * 8/7 ],
				"7:6": [ freq: 440 * 7/6 ],
				"13:11": [ freq: 440 * 13/11 ],
				"6:5": [ freq: 440 * 6/5 ],
				"5:4": [ freq: 440 * 5/4 ],
				"4:3": [ freq: 440 * 4/3 ],
				"3:2": [ freq: 440 * 3/2 ],
				"5:3": [ freq: 440 * 5/3 ],
				"7:4": [ freq: 440 * 7/4 ],
				"16:9": [ freq: 440 * 16/9 ],
				"9:5": [ freq: 440 * 9/5 ],
				"bass": [ freq: 110 ],
		));

		// drones
		this.droneSynthWrap("DeadAnimalJuice", {|argA,argB|
			var sig, rate, buf;

			rate = argA.linlin(0.0,1.0,2,30);

			sig = ClipNoise.ar(Decay2.ar(Dust.ar(rate), 0.01, 0.5, 0.5));

			sig = {BPF.ar(
				sig,
				LFNoise1.kr(rate*2.3).range(10,10000),
				LFNoise0.kr(rate*1.7).range(0.0005,0.2)
			)}.dup(20);

			buf = LocalBuf.new(SampleRate.ir * 0.5, 1);

			sig = sig + BufAllpassN.ar(
				buf,
				Decay2.ar(sig, 0.001, LFNoise1.kr(rate*0.1).range(0.002,0.005)),
				LFNoise0.kr(rate*2.9).range(0.05,0.5),
				LFNoise0.kr(rate*2.7).range(0.5,3)
			);

			sig = Median.ar(
				10,
				Integrator.ar(
					sig,
					LFNoise1.kr(rate*0.5).range(0.001,0.999)
				)
			);

			sig = Fold.ar(sig, -0.8, 0.8);

			sig = MantissaMask.ar(sig, LFNoise0.kr(rate*1.9).range(3,14).round);

			sig = LPF.ar(sig, argB.linexp(0.0,1.0,100,2000));
		});

		this.feedbackDroneSynthWrap("ChirpSpiralFB", {|input,argA,argB|
			var sig, in, buf;

			in = Mix.new(input);

			sig = PitchShift.ar(
				in,
				0.2,
				LFNoise0.kr(
					LFNoise0.kr(Rand(0.5,2)).range(0.5,2)
				).range(0.95,0.95.reciprocal)
			);

			buf = LocalBuf.new(SampleRate.ir * 2, 1);

			sig = BufAllpassN.ar(buf, sig, LFNoise0.kr(argA.linlin(0,1,0.2,2)).range(0.05,0.5), 2);

			sig = MantissaMask.ar(sig,argB.linlin(0,1,0,16).round);

			sig = LeakDC.ar(sig);
		});

		this.feedbackDroneSynthWrap("PhaseModFB", {|input,argA,argB|
			var sig, in, buf;

			in = Mix.new(input);

			sig = SinOsc.ar(Rand(10,30),in.range(-0.5pi,0.5pi));

			buf = LocalBuf.new(SampleRate.ir * 2, 1);

			sig = BufCombN.ar(buf, sig, LFNoise0.kr(argA.linlin(0,1,0.2,2)).range(0.05,0.5), 2);

			sig = MantissaMask.ar(sig,argB.linlin(0,1,0,16).round);

			sig = LeakDC.ar(sig);
		});

		this.feedbackDroneSynthWrap("PlatapusFB", {|input,argA,argB|
			var sig, upChan, downChan, buf;

			upChan = SelectXFocus.ar(
				LFNoise1.kr(LFNoise0.kr(Rand(0.05,0.3)).range(0.5,3)).range(0,input.size-1),
				input,
				LFNoise1.kr(LFNoise0.kr(Rand(0.05,0.3)).range(0.5,3)).range(0.5,1.5)
			);

			downChan = SelectXFocus.ar(
				LFNoise1.kr(LFNoise0.kr(Rand(0.05,0.3)).range(0.5,3)).range(0,input.size-1),
				input,
				LFNoise1.kr(LFNoise0.kr(Rand(0.05,0.3)).range(0.5,3)).range(0.5,1.5)
			);

			sig = Compander.ar(
				downChan,
				upChan,
				LFNoise1.kr(LFNoise0.kr(Rand(0.05,0.3)).range(0.5,3)).range(0,1),
				LFNoise1.kr(LFNoise0.kr(Rand(0.05,0.3)).range(0.5,3)).range(0.1,0.8),
				LFNoise1.kr(LFNoise0.kr(Rand(0.05,0.3)).range(0.5,3)).range(0.1,0.8),
				LFNoise1.kr(LFNoise0.kr(Rand(0.05,0.3)).range(0.5,3)).range(0.002,0.2),
				LFNoise1.kr(LFNoise0.kr(Rand(0.05,0.3)).range(0.5,3)).range(0.2,0.002)
			);

			sig = RLPF.ar(
				sig,
				argA.linlin(0.0,1.0,500,3000),
				argB.linlin(0.0,1.0,2.0,0.1)
			);

			sig = FreqShift.ar(
				sig,
				LFNoise1.kr(LFNoise0.kr(Rand(0.05,0.3)).range(0.5,3)).range(-200,200)
			);

			buf = LocalBuf.new(SampleRate.ir * 0.5, 1);

			sig = sig + BufDelayN.ar(
				buf,
				sig.fold2,
				LFNoise2.kr(LFNoise2.kr(Rand(0.05,0.1)).range(0.03,0.4)).range(0.06,0.4),
				0.95
			);

			sig = LeakDC.ar(sig*0.9);
		});

		this.feedbackDroneSynthWrap("DelayFB", {|input,argA,argB|
			var sig, in, cheby, buf;

			cheby = LocalBuf(512).set( Signal.chebyFill(513,[0.5,0.25,0.25,0.3]) );

			in = SelectXFocus.ar(
				LFNoise1.kr(LFNoise0.kr(Rand(0.05,0.3)).range(0.5,3)).range(0,input.size-1),
				input,
				LFNoise1.kr(LFNoise0.kr(Rand(0.05,0.3)).range(0.5,3)).range(0.5,1.5)
			);

			sig = Slew.ar(
				(in * Lag.kr( argB.linlin(0.0,1.0,1.0,5.0), 0.2 )).fold2,
				Lag.kr( argA.linlin(0.0,1.0,1.0,1000.0), 0.2 ),
				Lag.kr( argB.linlin(0.0,1.0,1.0,1000.0), 0.2 )
			);

			buf = LocalBuf.new(SampleRate.ir, 1);

			sig = BufDelayN.ar(
				buf,
				sig * argA.linlin(0.0,1.0,1.0,1.5),
				Rand(0.1,1)
			).softclip;

			sig = LeakDC.ar(Shaper.ar(cheby,sig));

		});

		this.feedbackDroneSynthWrap("PinkSpagetti", {|input,argA,argB|
			var sig, in, cheby, buf;

			sig = input;

			sig = LeakDC.ar(sig);

			cheby = LocalBuf(512).set( Signal.chebyFill(513,[0.5,0.25,0.25,0.3]) );

			buf = LocalBuf.new(SampleRate.ir * 0.1, 1);

			sig = Latch.ar(
				sig,
				Impulse.ar(
					BufDelayN.ar(
						buf,
						ZeroCrossing.ar(sig) * argA.linlin(0.0,1.0,0.125,0.5),
						argB.linlin(0.0,1.0,0.001,0.1)
					)
				)
			) + LFNoise0.kr(0.1);

			sig = DFM1.ar( sig, argA.linlin(0.0, 1.0, 50.0, 700.0), 0.75, type:1);

			sig = MoogLadder.ar(sig,argB.linlin(0.0,1.0,3000,7000),0.75);

			sig = Shaper.ar(cheby,sig);

			sig = LeakDC.ar(sig);

		});

		this.feedbackDroneSynthWrap("Comber", {|input,argA,argB|
			var sig, buf;

			sig = input * 2;

			sig = FreqShift.ar(sig, Lag.kr(Impulse.kr(Rand(0.5,0.6)),0.02).range(LFNoise0.kr(Rand(10.0,12.0)).range(-500.0,50.0),0.0));

			buf = LocalBuf.new(SampleRate.ir * 0.5, 1);

			sig = BufCombC.ar(
				buf,
				(sig*1.1).softclip,
				LFNoise0.kr(LFNoise0.kr(Rand(0.5,2)).range(0.5,2)).range(0.9,1.1) * argB.linlin(0.0,1.0,0.05,0.4),
				argA.linlin(0.0,1.0,0.5,2.0)
			) * 0.8;

			sig = LeakDC.ar((sig*2).softclip);

		});

		this.feedbackDroneSynthWrap("Dispersion", {|input,argA,argB|
			var sig;

			sig = (input * 3).distort;

			sig = PitchShift.ar(sig, 1, 0.99, argA.linlin(0.0,1.0,0.0,2.0), argB.linlin(0.0,1.0,0.0,2.0));

			sig = sig + RLPF.ar(sig.softclip, 300, LFNoise0.kr(Rand(10,11).range(1.0,0.2)), 2).fold;

			sig = HPF.ar(sig, 40);

			sig = LeakDC.ar(sig);
		});

		this.feedbackDroneSynthWrap("Recursion", {|input,argA,argB, out|
			var sig;

			sig = (input * 3).distort;

			5.do{|i|
				sig = sig + AllpassN.ar(sig, 1 / (i**2), 1 / (i**2), 1);
			};

			sig = RLPF.ar(sig, argB.linlin(0.0,1.0,3000,500), argA.linlin(0.0,1.0,1.0,0.1)).distort;

			sig = PitchShift.ar(sig, 0.2, 0.9, 0.1, 0.1);

			sig = LeakDC.ar(FreqShift.ar(sig + InFeedback.ar(out),sig.fold.range(-50,1))).softclip;

		});

		this.feedbackDroneSynthWrap("Evisceration", {|input,argA,argB|
			var sig, buf;

			sig = (input * 5).softclip;

			buf = LocalBuf.new(SampleRate.ir * 0.2, 1);

			sig = BufDelayN.ar(buf, sig, LFNoise2.kr(LFNoise0.kr(Rand(0.5,3)).range(0.5,3)).range(0.05,0.2));

			sig = sig = InsideOut.ar(sig, argB.linlin(0.0,1.0,0.05,0.5));

			sig = Decay.ar(sig, argA.linlin(0.0,1.0,0.0001,0.001));

			sig = sig * Decay.ar(Dust.ar(argA.linlin(0.0,1.0,0.5,20)), argB.linlin(0.0,1.0,1,0.1));

			sig = LeakDC.ar(sig);

		});

		// effects
		//////// TODO: make a wrapper for the evneloping on the effects?
		(
			SynthDef("CrunchVerb",{|in=100, out=100, vol=1, gate=1, gain=1|
				var sig, env;

				env = EnvGen.kr(Env.asr(5,1,5,\sine), gate, doneAction:2);

				sig = In.ar(in,1);

				sig = (sig*gain).softclip;

				sig = GVerb.ar(sig, 40, 0.45, 0.25, 0.1, 0, -2.dbamp, -23.dbamp, -33.dbamp, 40);

				sig = (sig*gain).distort * gain.reciprocal;

				sig = LeakDC.ar(sig);

				sig = sig * Lag.kr(vol.linlin(0.0,1.0,0.0,0.3), 1);

				Out.ar(out,Mix.new(sig)*env);
				}, variants: (
					gain1: [ gain: 1 ],
					gain2: [ gain: 2 ],
					gain3: [ gain: 3 ],
					gain6: [ gain: 6 ],
					gain12: [ gain: 12 ],
					gain24: [ gain: 24 ]
			)).store;
		);

		(
			SynthDef("DifferenceToneAmp",{|in=100, out=100, vol=1, gate=1, gain=64|
				var sig, amp=1, env;

				env = EnvGen.kr(Env.asr(5,1,5,\sine), gate, doneAction:2);

				sig = In.ar(in, 1);

				sig = LPF.ar((sig*Lag.kr(gain,0.1)).distort.fold2,5000,amp);

				sig = sig + LPF.ar(sig.distort, 330, gain);

				sig = HPF.ar(sig,40);

				sig = LeakDC.ar(sig * gain.linlin(15,64,1,0.3));

				Out.ar(out,sig*env*Lag.kr(vol,1));
				}, variants: (
					gain2: [ gain: 2 ],
					gain3: [ gain: 3 ],
					gain5: [ gain: 5 ],
					gain15: [ gain: 15 ],
					gain24: [ gain: 24 ],
					gain48: [ gain: 48 ],
					gain64: [ gain: 64 ],
			)).store;
		);

		(
			SynthDef("InsideOut",{|in=100, out=100, vol=1, gate=1|
				var sig, env;

				env = EnvGen.kr(Env.asr(5,1,5,\sine), gate, doneAction:2);

				sig = In.ar(in,1);

				sig = InsideOut.ar(sig, 0.05);

				sig = LeakDC.ar(sig);

				sig = sig * Lag.kr(vol.linlin(0.0,1.0,0.0,0.5), 1);

				Out.ar(out,Mix.new(sig)*env);
			}).store;
		);
	}

	// SynthDef factory handling things specific to rhythm synths and speaker configurations
	rhythmSynthWrap
	{|name,function,variantList,decayTime=0.05|
		switch(Manticore.speakerConfiguration,
			\stereo, {
				SynthDef(name,{|in=100, out=0, gate=1, vol=1, argA=0, argB=0|
					var input, sig, tenv, env, panX, panY, panZ, amp;
					var inFeedback;

					input = In.ar(in,1);

					amp = TRand.ar(0.05,0.7,input);
					panX = Rand(-1,1);
					panY = Rand(-1,1);
					panZ = Rand(-1,1);

					vol = Lag.kr(vol,1);

					env = EnvGen.kr(Env.asr(0.005, 0.3, 0.5), gate, doneAction:2);

					tenv = EnvGen.ar(Env.perc(0.001,decayTime,1,'linear'),input);

					argA = Lag.kr(argA,0.5);
					argB = Lag.kr(argB,0.5);

					sig = SynthDef.wrap(function, nil, [argA, argB, input]);

					sig = (sig * argA.linexp(0.0,1.0,1.0,160.0)).distort * tenv;

					inFeedback = InFeedback.ar(out,2);

					inFeedback = FreqShift.ar(inFeedback,-80);

					inFeedback = SelectX.ar(panX, inFeedback);

					inFeedback = AllpassC.ar(
						inFeedback,
						1,
						LFNoise0.kr(LFNoise0.kr(Rand(0.3,0.8)).range(0.3,0.8)).range(0.1,0.9),
						3
					);

					inFeedback = MoogLadder.ar(
						inFeedback,
						Rand(1200,5000),
						Rand(0.2,0.9)
					) * argB.linlin(0.0,1.0,0.5,20.0);

					sig = sig + inFeedback.distort;

					sig = LeakDC.ar(sig);

					Out.ar(out,Pan2.ar(sig*env*vol,panX,amp));
					},
					variants: variantList
				).store;
			},
			\quad, {
				SynthDef(name,{|in=100, out=0, gate=1, vol=1, argA=0, argB=0|
					var input, sig, tenv, env, panX, panY, panZ, amp;

					input = In.ar(in,1);

					amp = TRand.ar(0.05,0.7,input);
					panX = Rand(-1,1);
					panY = Rand(-1,1);
					panZ = Rand(-1,1);

					vol = Lag.kr(vol,1);

					env = EnvGen.kr(Env.asr(0.005, 0.3, 0.5), gate, doneAction:2);

					tenv = EnvGen.ar(Env.perc(0.001,decayTime,1,'linear'),input);

					sig = SynthDef.wrap(function, nil, [argA, argB, input]);

					Out.ar(out,Pan4.ar(sig*env*tenv*vol,panX,panY,amp));
					},
					variants: variantList
				).store;
			},
			\cube, {
				SynthDef(name,{|in=100, out=0, gate=1, vol=1, argA=0, argB=0|
					var input, sig, tenv, env, panX, panY, panZ, amp;

					input = In.ar(in,1);

					amp = TRand.ar(0.05,0.7,input);
					panX = Rand(-1,1);
					panY = Rand(-1,1);
					panZ = Rand(-1,1);

					vol = Lag.kr(vol,1);

					env = EnvGen.kr(Env.asr(0.01, 0.3, 0.5), gate, doneAction:2);

					tenv = EnvGen.ar(Env.perc(0.001,decayTime,1,'linear'),input);

					sig = SynthDef.wrap(function, nil, [argA, argB, input]);

					Out.ar(out,CubePan.ar(sig*env*tenv*vol,panX,panY,panZ,amp));
					},
					variants: variantList
				).store;
			}
		);
	}

	droneSynthWrap
	{|name,function,variantList|
		switch(Manticore.speakerConfiguration,
			\stereo, {
				SynthDef(name,{|out=0, amp=0.3, vol=1, gate=1, argA=0, argB=0|
					var sig, rate, env, panX, panY, panZ;

					amp = Rand(0.2,0.7);
					panX = Rand(-1,1);
					panY = Rand(-1,1);
					panZ = Rand(-1,1);

					argA = Lag.kr(argA,0.5);
					argB = Lag.kr(argB,0.5);

					vol = Lag.kr(vol,1);

					env = EnvGen.kr(Env.asr(2,1,0.5,\sine), gate, doneAction:2);

					sig = SynthDef.wrap(function, nil, [argA, argB, out]);

					Out.ar(out,Pan2.ar(sig, panX, amp*vol*env));
					},
					variants: variantList
				).store;
			},
			\quad, {
				SynthDef(name,{|out=0, amp=0.3, vol=1, gate=1, argA=0, argB=0|
					var sig, rate, env, panX, panY, panZ;

					amp = Rand(0.2,0.7);
					panX = Rand(-1,1);
					panY = Rand(-1,1);
					panZ = Rand(-1,1);

					argA = Lag.kr(argA,0.5);
					argB = Lag.kr(argB,0.5);

					vol = Lag.kr(vol,1);

					env = EnvGen.kr(Env.asr(2,1,0.5,\sine), gate, doneAction:2);

					sig = SynthDef.wrap(function, nil, [argA, argB, out]);

					Out.ar(out,Pan4.ar(sig, panX, panY, amp*vol*env));
					},
					variants: variantList
				).store;
			},
			\cube, {
				SynthDef(name,{|out=0, amp=0.3, vol=1, gate=1, argA=0, argB=0|
					var sig, rate, env, panX, panY, panZ;

					amp = Rand(0.2,0.7);
					panX = Rand(-1,1);
					panY = Rand(-1,1);
					panZ = Rand(-1,1);

					argA = Lag.kr(argA,0.5);
					argB = Lag.kr(argB,0.5);

					vol = Lag.kr(vol,1);

					env = EnvGen.kr(Env.asr(2,1,0.5,\sine), gate, doneAction:2);

					sig = SynthDef.wrap(function, nil, [argA, argB, out]);

					Out.ar(out,CubePan.ar(sig*env*vol,panX,panY,panZ,amp));
					},
					variants: variantList
				).store;
			}
		)
	}

	feedbackDroneSynthWrap
	{|name,function,variantList|
		switch(Manticore.speakerConfiguration,
			\stereo, {
				SynthDef(name,{|out=0, amp=0.3, vol=1, gate=1, argA=0, argB=0|
					var sig, input, rate, env, panX, panY, panZ;

					input = InFeedback.ar(out, 2);

					amp = Rand(0.2,0.7);
					panX = Rand(-1,1);
					panY = Rand(-1,1);
					panZ = Rand(-1,1);

					argA = Lag.kr(argA,0.5);
					argB = Lag.kr(argB,0.5);

					vol = Lag.kr(vol,0.5);

					env = EnvGen.kr(Env.asr(2,1,0.5,\sine), gate, doneAction:2);

					sig = SynthDef.wrap(function, nil, [input, argA, argB, out]);

					Out.ar(out,Pan2.ar(sig, panX, amp*vol*env));
					},
					variants: variantList
				).store;
			},
			\quad, {
				SynthDef(name,{|out=0, amp=0.3, vol=1, gate=1, argA=0, argB=0|
					var sig, input, rate, env, panX, panY, panZ;

					input = InFeedback.ar(out, 4);

					amp = Rand(0.2,0.7);
					panX = Rand(-1,1);
					panY = Rand(-1,1);
					panZ = Rand(-1,1);

					argA = Lag.kr(argA,0.5);
					argB = Lag.kr(argB,0.5);

					vol = Lag.kr(vol,0.5);

					env = EnvGen.kr(Env.asr(2,1,0.5,\sine), gate, doneAction:2);

					sig = SynthDef.wrap(function, nil, [input, argA, argB, out]);

					Out.ar(out,Pan4.ar(sig, panX, panY, amp*vol*env));
					},
					variants: variantList
				).store;
			},
			\cube, {
				SynthDef(name,{|out=0, amp=0.3, vol=1, gate=1, argA=0, argB=0|
					var sig, input, rate, env, panX, panY, panZ;

					input = InFeedback.ar(out, 8);

					amp = Rand(0.2,0.7);
					panX = Rand(-1,1);
					panY = Rand(-1,1);
					panZ = Rand(-1,1);

					argA = Lag.kr(argA,0.5);
					argB = Lag.kr(argB,0.5);

					vol = Lag.kr(vol,0.5);

					env = EnvGen.kr(Env.asr(2,1,0.5,\sine), gate, doneAction:2);

					sig = SynthDef.wrap(function, nil, [input, argA, argB, out]);

					Out.ar(out,CubePan.ar(sig*env*vol,panX,panY,panZ,amp));
					},
					variants: variantList
				).store;
			}
		)
	}

}

/////////// Additional Classes  ///////////

TarasqueServerItems
{
	classvar <colotomyGroup;
	classvar <patternGroup;
	classvar <audioGroup;
	classvar <effectGroup;
	classvar <defaultBus;
	classvar <audioToEffectsBus;
	classvar <activeColotomy;
	classvar <effectSynths;
	classvar <>currentVolume;

	*init
	{
		colotomyGroup = Group.new;
		patternGroup = Group.after(colotomyGroup);
		audioGroup = Group.after(patternGroup);
		effectGroup = Group.after(audioGroup);
		defaultBus = Bus.audio(Server.default, 5);
		currentVolume = 1;

		switch(Manticore.speakerConfiguration,
			\stereo, {
				audioToEffectsBus = Bus.audio(Server.default, 2);
				effectSynths = Array.fill(2,{nil});
			},
			\quad, {
				audioToEffectsBus = Bus.audio(Server.default, 4);
				effectSynths = Array.fill(4,{nil});
			},
			\cube, {
				audioToEffectsBus = Bus.audio(Server.default, 8);
				effectSynths = Array.fill(8,{nil});
			}
		);

		//activeColotomy = Dictionary.new;
		effectSynths.size.do{|i|
			effectSynths[i] = Synth(
				"CrunchVerb",
				[
					"in",(TarasqueServerItems.audioToEffectsBus.index+i),
					"out",i

				],
				effectGroup
			);
		};

	}

	*changeEffects
	{|synthName|
		effectSynths.size.do{|i|
			effectSynths[i].release;

			effectSynths[i] = Synth(
				synthName,
				[
					"in",(TarasqueServerItems.audioToEffectsBus.index+i),
					"out",i,
					"vol", currentVolume

				],
				effectGroup
			);
		};
	}

	*free{
		colotomyGroup.free;
		patternGroup.free;
		audioGroup.free;
		effectGroup.free;
		defaultBus.free;
		audioToEffectsBus.free;
	}
}

TarasqueColorScheme
{
	*synthZoneCenterColor
	{|category|
		if(category == "c",{
			//^Color.new(0.9, 0.0, 0.0);
			^Color.new(0.0, 0.9, 0.0);
		});

		if(category == "p",{
			//^Color.new(0.0, 0.9, 0.0);
			^Color.new(0.0, 0.0, 0.9);
		});

		if(category == "r",{
			//^Color.new(0.0, 0.0, 0.9);
			^Color.new(0.9, 0.9, 0.0);
		});

		if(category == "d",{
			//^Color.new(0.9, 0.0, 0.9);
			^Color.new(0.9, 0.9, 0.0);
		});

		if(category == "h",{
			^Color.new(0.9, 0.9, 0.0);
		});

		if(category == "a",{
			^Color.new(0.0, 0.0, 0.7);
		});
	}

	*synthZoneOuterColor
	{|category|
		if(category == "c",{
			//^Color.new(0.4, 0.0, 0.05);
			^Color.new(0.05, 0.4, 0.0);
		});

		if(category == "p",{
			//^Color.new(0.05, 0.4, 0.0);
			^Color.new(0.0, 0.05, 0.4);
		});

		if(category == "r",{
			//^Color.new(0.0, 0.05, 0.4);
			^Color.new(0.4, 0.4, 0.05);
		});

		if(category == "d",{
			//^Color.new(0.4, 0.05, 0.4);
			^Color.new(0.4, 0.4, 0.05);
		});

		if(category == "h",{
			^Color.black;
		});

		if(category == "a",{
			//^Color.new(0.4, 0.05, 0.4);
			^Color.new(0.7, 0.0, 0.7);
		});
	}

	*synthHandleActiveColor
	{|category|
		if(category == "c",{
			^Color.new(0.7, 0.0, 0.0);
		});

		if(category == "p",{
			^Color.new(0.0, 0.7, 0.0);
		});

		if(category == "r",{
			^Color.new(0.0, 0.0, 0.7);
		});

		if(category == "d",{
			^Color.new(0.7, 0.0, 0.7);
		});

		if(category == "h",{
			^Color.new(0.7, 0.7, 0.0);
		});

		if(category == "a",{
			^Color.new(0.0, 0.7, 0.7);
		});
	}

	*synthHandleInactiveColor
	{|category|
		if(category == "c",{
			^Color.new(0.7, 0.0, 0.0);
		});

		if(category == "p",{
			^Color.new(0.0, 0.4, 0.0);
		});

		if(category == "r",{
			^Color.new(0.0, 0.0, 0.4);
		});

		if(category == "d",{
			^Color.new(0.4, 0.0, 0.4);
		});

		if(category == "h",{
			^Color.new(0.4, 0.4, 0.0);
		});

		if(category == "a",{
			^Color.new(0.0, 0.7, 0.7);
		});
	}
}

TarasqueDesktop
{
	classvar mousePos;	// hold the mouse position in the interface
	classvar pMousePos;	// holds the previous mouse position
	var selectionArea = nil;
	var cursors; // holds all cursor syncObjects
	classvar <desktopObjects;
	var selectedObjects;
	var osc;

	var <colotomyList;
	var <patternList;
	var <rthythmList;
	var <droneList;
	var <handleList;
	var <areaList;

	*new
	{
		^super.new.init;
	}

	init
	{

		osc = List.new;
		cursors = Dictionary.new;
		desktopObjects = Dictionary.new;
		selectedObjects = Set.new;
		mousePos = 0@0;
		pMousePos = mousePos;

		TarasqueServerItems.init;

		colotomyList = List.new;
		patternList = List.new;
		rthythmList = List.new;
		droneList = List.new;
		handleList = List.new;
		areaList = List.new;

		this.setupOSClisteners;

		OSCthulhu.addSyncObject(
			OSCthulhu.userName.asString++"Curs",
			Manticore.group,
			"curs",
			[
				OSCthulhu.userName,
				mousePos.x,
				mousePos.y
			]
		);
	}

	*deselectAll
	{

		desktopObjects.keysDo{|obj|
			OSCthulhu.setSyncArg(obj, 2, "none");
		};

	}

	setupOSClisteners
	{
		// addSyncObject
		osc.add(OSCthulhu.onAddSyncObject(\addObjects, {|msg|
			var oscAddr, objName, group, subGroup, args;

			# oscAddr, objName, group, subGroup = msg;
			args = msg.copyRange(4,msg.size-1);

			//			args.do{|item,i| (i.asString ++ ":" + item).postln };

			// cursors
			if(subGroup.asString == "curs",{
				cursors.put(objName,TarasqueCursor(args[0], Point(args[1],args[2])));
			});

			// desktop objects
			if(subGroup.asString.contains("o_"),{
				desktopObjects.put(objName,
					TarasqueDesktopObject.new(
						subGroup.asSymbol,
						Point(args[0],args[1])
					)
				);
			});

			if(subGroup.asString.contains("c_"),{
				desktopObjects.put(objName,
					TarasqueColotomyObject.new(
						subGroup.asSymbol,
						Point(args[0],args[1])
					)
				);
				colotomyList.add(objName);
				this.updateObjects(objName);
			});

			if(subGroup.asString.contains("p_"),{
				desktopObjects.put(objName,
					TarasquePatternObject.new(
						subGroup.asSymbol,
						Point(args[0],args[1])
					)
				);
				patternList.add(objName);
				this.updateObjects(objName);
			});

			if(subGroup.asString.contains("r_"),{
				desktopObjects.put(objName,
					TarasqueRthymicObject.new(
						subGroup.asSymbol,
						Point(args[0],args[1])
					)
				);
				rthythmList.add(objName);
				this.updateObjects(objName);
			});

			if(subGroup.asString.contains("d_"),{
				desktopObjects.put(objName,
					TarasqueDroneObject.new(
						subGroup.asSymbol,
						Point(args[0],args[1])
					)
				);
				droneList.add(objName);
				this.updateObjects(objName);
			});

			if(subGroup.asString.contains("h_"),{
				desktopObjects.put(objName,
					TarasqueControlObject.new(
						subGroup.asSymbol,
						Point(args[0],args[1])
					)
				);
				handleList.add(objName);
				this.updateObjects(objName);
			});

			if(subGroup.asString.contains("a_"),{
				desktopObjects.put(objName,
					TarasqueAreaObject.new(
						subGroup.asSymbol,
						Point(args[0],args[1])
					)
				);
				areaList.add(objName);
				this.updateObjects(objName);
			});

			});
		);

		// setSyncArg
		osc.add(OSCthulhu.onSetSyncArg(\updateObjects, {|msg|
			var oscAddr, objName, argIndex, argValue, group, subGroup;

			# oscAddr, objName, argIndex, argValue, group, subGroup = msg;
			//			msg.do{|item,i| (i.asString ++ ":" + item).postln };

			// cursors
			if(subGroup.asString == "curs", {
				switch(
					argIndex,
					1, { cursors.at(objName).pos.x = argValue; },
					2, { cursors.at(objName).pos.y = argValue; }
				);
			});

			// desktop objects
			if(
				subGroup.asString.contains("o_") ||
				subGroup.asString.contains("c_") ||
				subGroup.asString.contains("p_") ||
				subGroup.asString.contains("r_") ||
				subGroup.asString.contains("d_") ||
				subGroup.asString.contains("h_") ||
				subGroup.asString.contains("a_"), {
					switch(
						argIndex,
						0, { desktopObjects.at(objName).setX(argValue); this.updateObjects(objName); },
						1, { desktopObjects.at(objName).setY(argValue); this.updateObjects(objName); },
						2, {
							desktopObjects.at(objName).selectedBy = argValue.asString;

							if(argValue.asString == OSCthulhu.userName.asString,{
								selectedObjects.add(objName);
								},{
									selectedObjects.remove(objName);
							});

						}
					);
			});

			});
		);

		// removeSyncObjects
		osc.add(OSCthulhu.onRemoveSyncObject(\removeObjects, {|msg|
			var oscAddr, objName, group, subGroup;

			# oscAddr, objName, group, subGroup = msg;

			if(subGroup.asString == "curs", {
				cursors.removeAt(objName);
			});

			if(desktopObjects.at(objName).category == "h",{
				rthythmList.do{|rhythmName|
					desktopObjects.at(rhythmName).controlConnections.do{|slot,i|
						if(slot.value == desktopObjects.at(objName),{
							desktopObjects.at(rhythmName).controlConnections[i] = nil;
						});
					};
				};

				droneList.do{|droneName|
					desktopObjects.at(droneName).controlConnections.do{|slot,i|
						if(slot.value == desktopObjects.at(objName),{
							desktopObjects.at(droneName).controlConnections[i] = nil;
						});
					};
				};
			});

			desktopObjects.at(objName).free;
			desktopObjects.removeAt(objName);
			selectedObjects.remove(objName);

			colotomyList.remove(objName);
			patternList.remove(objName);
			rthythmList.remove(objName);
			droneList.remove(objName);
			handleList.remove(objName);
			areaList.remove(objName);
			})
		);
	}

	// updates connections when an object is added, moved, or removed
	updateObjects
	{|changedObject|
		var objCategory;
		objCategory = desktopObjects.at(changedObject).category;

		// if a colotomyObject is being updated
		if(objCategory == "c",{
			// update all patternObjects when a colotomyObject is changed
			patternList.do{|patternName|
				desktopObjects.at(patternName).attemptConnection(desktopObjects.at(changedObject));
			};
		});

		// if a patternObject is being updated
		if(objCategory == "p",{

			// check all colotomyObjects when a patternObject is changed
			colotomyList.do{|colotomyName|
				desktopObjects.at(changedObject).attemptConnection(desktopObjects.at(colotomyName));
			};


			// update all rhythmObjects when a patternObject is changed
			rthythmList.do{|rhythmName|
				desktopObjects.at(rhythmName).attemptConnection(desktopObjects.at(changedObject));
			};

		});

		// if a rhythmObject is being updated
		if(objCategory == "r",{

			patternList.do{|patternName|
				desktopObjects.at(changedObject).attemptConnection(desktopObjects.at(patternName));
			};

			handleList.do{|handleName|
				desktopObjects.at(changedObject).attemptControl(desktopObjects.at(handleName));
			};

			areaList.do{|areaName|
				if(desktopObjects.at(changedObject).origin.dist(desktopObjects.at(areaName).origin) <= desktopObjects.at(areaName).zoneRad,{
					desktopObjects.at(changedObject).synth.set(
						desktopObjects.at(areaName).argName,
						desktopObjects.at(areaName).argValue
					);
				});
			};

		});

		// if a droneObject is being updated
		if(objCategory == "d",{

			handleList.do{|handleName|
				desktopObjects.at(changedObject).attemptControl(desktopObjects.at(handleName));
			};

			areaList.do{|areaName|
				if(desktopObjects.at(changedObject).origin.dist(desktopObjects.at(areaName).origin) <= desktopObjects.at(areaName).zoneRad,{
					desktopObjects.at(changedObject).synth.set(
						desktopObjects.at(areaName).argName,
						desktopObjects.at(areaName).argValue
					);
				});
			};
		});

		// if a handleObject is being updated
		if(objCategory == "h",{

			rthythmList.do{|rhythmName|
				desktopObjects.at(rhythmName).attemptControl(desktopObjects.at(changedObject));
			};

			droneList.do{|rhythmName|
				desktopObjects.at(rhythmName).attemptControl(desktopObjects.at(changedObject));
			};
		});

		// if an areaObject is being updated
		if(objCategory == "a",{

			rthythmList.do{|rhythmName|
				if(desktopObjects.at(rhythmName).origin.dist(desktopObjects.at(changedObject).origin) <= desktopObjects.at(changedObject).zoneRad,{
					desktopObjects.at(rhythmName).synth.set(
						desktopObjects.at(changedObject).argName,
						desktopObjects.at(changedObject).argValue
					);
				});
			};

			droneList.do{|rhythmName|
				if(desktopObjects.at(rhythmName).origin.dist(desktopObjects.at(changedObject).origin) <= desktopObjects.at(changedObject).zoneRad,{
					desktopObjects.at(rhythmName).synth.set(
						desktopObjects.at(changedObject).argName,
						desktopObjects.at(changedObject).argValue
					);
				});
			};
		});
	}

	getView
	{
		var scrollView, interface;

		scrollView = [ScrollView(),stretch:6];

		interface = UserView(scrollView[0], Rect(0,0,1200,900));
		interface.background_(Color.gray(0.5));
		interface.clearOnRefresh = true;
		interface.animate = true;
		interface.mouseOverAction_({|view, x, y| interface.focus; });

		//keyboard support
		interface.keyDownAction = {|view, char, modifiers, unicode, keycode|

			switch(char,
				$x, {	// delete

					if(selectedObjects.size > 0,{

						selectedObjects.do{|item|
							OSCthulhu.removeSyncObject(item);
						};

						selectedObjects.clear;
					});
				},
				$a, {	// select all
					if(selectedObjects.size == 0,{
						desktopObjects.keysValuesDo{|key,obj|
							if(obj.selectedBy == "none",{
								OSCthulhu.setSyncArg(key, 2, OSCthulhu.userName);
							});
						};
						},{
							selectedObjects.do{|obj|
								if(desktopObjects.at(obj).selectedBy == OSCthulhu.userName.asString,{
									OSCthulhu.setSyncArg(obj, 2, "none");
								});
							};
					});
				},
				$j, {
					selectedObjects.do{|obj|
						var jittered;
						jittered = desktopObjects.at(obj).origin;

						jittered.x = jittered.x + rrand(-10,10);
						jittered.y = jittered.y + rrand(-10,10);

						OSCthulhu.setSyncArg(obj, 0, jittered.x.clip(0,interface.bounds.width));
						OSCthulhu.setSyncArg(obj, 1, jittered.y.clip(0,interface.bounds.height));
					}
				},
				$r, {
					selectedObjects.do{|obj|
						var newObject, num, origin;

						if(desktopObjects.at(obj).category == "r",{

							origin = desktopObjects.at(obj).origin;

							OSCthulhu.removeSyncObject(obj);
							selectedObjects.remove(obj);

							newObject = Tarasque.rhythmObjectList[Tarasque.scoreIndex].choose;

							num = rrand(1000,9999);

							{
								0.1.wait;
								OSCthulhu.addSyncObject(
									newObject ++ num,
									Manticore.group,
									newObject,
									[origin.x,origin.y,"none"]
								);
							}.fork;
						});
					}
				}
			);
		};

		interface.keyUpAction = {|view, char, modifiers, unicode, keycode|

		};

		// mouse support
		interface.mouseDownAction = {|view, x, y, modifiers, buttonNumber, clickCount|
			var objectSelected = false;

			desktopObjects.keysValuesDo{|key,obj|
				if(obj.origin.dist(mousePos) <= obj.handleRad,{

					objectSelected = true;

					if(obj.selectedBy == "none",{

						selectedObjects.add(key);
						OSCthulhu.setSyncArg(key, 2, OSCthulhu.userName);

					});

					if(modifiers == 131072 && obj.selectedBy == OSCthulhu.userName.asString,{

						OSCthulhu.setSyncArg(key, 2, "none");
					});

					selectedObjects.do{|sel|
						desktopObjects.at(sel).clickOffset = desktopObjects.at(sel).origin - mousePos;
					};
				});
			};

			// deselect, create selection area
			if(objectSelected == false,{
				selectionArea = TarasqueSelectionArea.new(mousePos);
				selectedObjects.do{|obj|
					OSCthulhu.setSyncArg(obj, 2, "none");
				};
				selectedObjects.clear;
			});

		};

		interface.mouseUpAction = {|view, x, y, modifiers|

			// object selection
			if(selectionArea.notNil,{
				desktopObjects.keysValuesDo{|key,obj|

					if(modifiers == 131072,{
						if(selectionArea.area.containsPoint(obj.origin),{
							if(obj.selectedBy == OSCthulhu.userName,{
								selectedObjects.add(key);
								OSCthulhu.setSyncArg(key, 2, "none");
							});
						});
						},{

							if(selectionArea.area.containsPoint(obj.origin),{
								if(obj.selectedBy == "none",{
									selectedObjects.add(key);
									OSCthulhu.setSyncArg(key, 2, OSCthulhu.userName);
								});
							});
					});
				};

				selectionArea.release;
				selectionArea = nil;
			});
		};

		interface.mouseOverAction = {|view,x,y|
			mousePos = x@y;
			OSCthulhu.setSyncArg(OSCthulhu.userName.asString++"Curs", 1, mousePos.x);
			OSCthulhu.setSyncArg(OSCthulhu.userName.asString++"Curs", 2, mousePos.y);
		};

		interface.mouseMoveAction = {|view,x,y|
			mousePos = x@y;
			OSCthulhu.setSyncArg(OSCthulhu.userName.asString++"Curs", 1, mousePos.x);
			OSCthulhu.setSyncArg(OSCthulhu.userName.asString++"Curs", 2, mousePos.y);

			selectedObjects.do{|sel|
				var obj;

				obj = desktopObjects.at(sel);

				OSCthulhu.setSyncArg(
					sel,
					0,
					(mousePos.x + obj.clickOffset.x).clip(0,interface.bounds.width)
				);
				OSCthulhu.setSyncArg(
					sel,
					1,
					(mousePos.y + obj.clickOffset.y).clip(0,interface.bounds.height)
				);
			};

			if(selectionArea.isNil == false,{
				selectionArea.update(mousePos);
			});

		};

		// drag support
		interface.canReceiveDragHandler = {View.currentDrag.isSymbol};

		interface.receiveDragHandler_({|view, x, y|
			var type;
			type = View.currentDrag.value.asString;

			if(type[0] == $m,{
				this.addMacro(type, x, y);
				},{
					OSCthulhu.addSyncObject(
						type ++ rrand(1000,9999), Manticore.group, type, [x,y,"none"]
					);
			});

		});

		// drawing
		interface.drawFunc = {

			// objects
			desktopObjects.do{|obj|
				obj.drawBottomLayer;
			};

			desktopObjects.do{|obj|
				obj.drawTopLayer;
			};

			desktopObjects.do{|obj|
				obj.drawText;
			};

			// show the selection area
			if(selectionArea.isNil == false,{
				selectionArea.draw;
			});

			// show all cursors
			cursors.keysValuesDo{|key,value|
				if(key.asString != (OSCthulhu.userName.asString++"Curs"),{
					value.draw;
				});
			};

		};

		^scrollView;
	}

	addMacro
	{|name, x, y|
		var macro;
		macro = name.split($_)[1].asSymbol;

		switch(macro,
			'DustyCombo',{
				this.addMacroSyncObjects([
					["c_Dusty", Point(0,0)],
					["p_NineTrip", Point(0,-125)],
					["p_NineTrip", Point(133,-35)],
					["p_Pentambeter", Point(55,116)],
					["p_Pentambeter", Point(-103,102)],
					["p_FiveShuffle", Point(-143,-41)],
					["a_mf", Point(97,-112)],
					["a_ff", Point(-70,68)]
				], x, y);
			},
			/*
			'OakStyleCombo',{
			this.addMacroSyncObjects([
			[ "p_Constant.eighth", Point( 1, -108 ) ],
			[ "p_Constant.eighth", Point( 125, 0 ) ],
			[ "p_Constant.eighth", Point( -3, 127 ) ],
			[ "p_Constant.eighth", Point( -126, 1 ) ],
			[ "r_OakStyle.one", Point( 0, -159 ) ],
			[ "r_OakStyle.one", Point( -1, -58 ) ],
			[ "r_OakStyle.two", Point( 126, -52 ) ],
			[ "r_OakStyle.two", Point( 126, 48 ) ],
			[ "r_OakStyle.four", Point( -3, 79 ) ],
			[ "r_OakStyle.four", Point( -4, 175 ) ],
			[ "r_OakStyle.four", Point( -125, -46 ) ],
			[ "r_OakStyle.four", Point( -130, 47 ) ]
			], x, y);
			},
			*/
			'BasicGrooveCombo',{
				this.addMacroSyncObjects([
					[ "p_NineTrip", Point( -1, -167 ) ],
					[ "p_Pentambeter", Point( 124, -100 ) ],
					[ "p_FiveShuffle", Point( 145, 40 ) ],
					[ "p_NineTrip", Point( 21, 120 ) ],
					[ "p_Pentambeter", Point( -108, 79 ) ],
					[ "p_FiveShuffle", Point( -126, -71 ) ],
					[ "a_mp", Point( 118, 141 ) ],
					[ "a_f", Point( -175, 13 ) ],
					[ "a_p", Point( 76, -166 ) ]
				], x, y);
			},
			'GrooveComboII',{
				this.addMacroSyncObjects([
					[ "p_Pentambeter", Point( 0, 177 ) ],
					[ "p_Pentambeter", Point( -177, 0 ) ],
					[ "p_Pentambeter", Point( 0, -177 ) ],
					[ "p_Pentambeter", Point( 177, 0 ) ],
					[ "p_Pentambeter", Point( 123, 112 ) ],
					[ "p_Pentambeter", Point( -129, 111 )],
					[ "p_Pentambeter", Point( -113, -121 ) ],
					[ "p_Pentambeter", Point( 113, -120 ) ],
					[ "p_NineTrip", Point( 59, -20 ) ],
					[ "p_NineTrip", Point( -42, 41 ) ],
					[ "a_mp", Point( 40, -97 ) ],
					[ "a_mf", Point( -51, 117 ) ]
				], x, y);
			},
			'GrooveComboIII',{
				this.addMacroSyncObjects([
					[ "p_Pentambeter", Point( 0, 177 ) ],
					[ "p_NineTrip", Point( -177, 0 ) ],
					[ "p_Pentambeter", Point( 0, -177 ) ],
					[ "p_NineTrip", Point( 177, 0 ) ],
					[ "p_Pentambeter", Point( 123, 112 ) ],
					[ "p_NineTrip", Point( -129, 111 )],
					[ "p_Pentambeter", Point( -113, -121 ) ],
					[ "p_NineTrip", Point( 113, -120 ) ],
					[ "p_FiveShuffle", Point( 59, -20 ) ],
					[ "p_NineTrip", Point( -42, 41 ) ],
					[ "a_mf", Point( 40, -97 ) ],
					[ "a_f", Point( -51, 117 ) ]
				], x, y);
			},
			'GrooveCombo',{
				this.addMacroSyncObjects([
					[ "p_WholeNoteWalk", Point( 0, 177 ) ],
					[ "p_Constant.quarter", Point( -177, 0 ) ],
					[ "p_1of3.half", Point( 0, -177 ) ],
					[ "p_2of3.quarter", Point( 177, 0 ) ],
					[ "p_Pentambeter", Point( 123, 112 ) ],
					[ "p_NineTrip", Point( -129, 111 )],
					[ "p_FiveShuffle", Point( -113, -121 ) ],
					[ "p_IceCreamPig", Point( 113, -120 ) ],
					[ "p_WoodenOctopus", Point( 59, -20 ) ],
					[ "p_2of3.whole", Point( -42, 41 ) ],
					[ "a_f", Point( 40, -97 ) ],
					[ "a_ff", Point( -51, 117 ) ]
				], x, y);
			}
		);
	}

	addMacroSyncObjects
	{|array, x, y|
		array.do{|pair|

			OSCthulhu.addSyncObject(
				pair[0] ++ rrand(1000,9999),
				Manticore.group,
				pair[0],
				[
					pair[1].x + x,
					pair[1].y + y,
					"none"
				]
			);

		};
	}

	free
	{
		osc.collect({|def| def.free; });
		cursors.collect({|i| i.free; });
		OSCthulhu.removeSyncObject(OSCthulhu.userName.asString++"Curs");

		TarasqueServerItems.free;
	}
}

TarasqueObjectWindow
{
	var osc, volSlider, objListView, currentScoreIndex;

	*new
	{|p|
		^super.new.init;
	}

	init
	{

		osc = List.new;
		this.setupOSClisteners;
		currentScoreIndex = 0;
	}

	setupOSClisteners
	{

		// addSyncObject
		osc.add(OSCthulhu.onAddSyncObject(\addGlobalEffectsValues, {|msg|
			var oscAddr, objName, group, subGroup, args;

			# oscAddr, objName, group, subGroup = msg;
			args = msg.copyRange(4,msg.size-1);

			if(subGroup.asString == "glbl", {
				if(args[0] != currentScoreIndex,{

					{objListView.removeAll}.defer;

					{objListView.canvas.layout_( this.pupulateObjectList(); )}.defer;

					currentScoreIndex = args[0];
				});
				{volSlider.value_( args[1] ); TarasqueServerItems.effectGroup.set("vol",args[1].asFloat); }.defer;
			});
			})
		);

		// setSyncArg
		osc.add(OSCthulhu.onSetSyncArg(\setGlobalEffectsValues, {|msg|
			var oscAddr, objName, argIndex, argValue, group, subGroup;

			# oscAddr, objName, argIndex, argValue, group, subGroup = msg;

			//			msg.do{|item,i| (i.asString ++ ":" + item).postln };

			if(subGroup.asString == "glbl", {
				switch(
					argIndex,
					0, {
						if(argValue != currentScoreIndex,{

							{this.pupulateObjectList(objListView.canvas); }.defer;

							currentScoreIndex = argValue;
						});
					},
					1, {
						{volSlider.value_( argValue )}.defer;
						TarasqueServerItems.effectGroup.set("vol",argValue.asFloat);
						TarasqueServerItems.currentVolume = argValue;
					}
				)
			});
			})
		);
	}

	getView
	{
		var container, objListLayout, globalControls, argsLayout, dynamicsLayout, dynamicsP, dynamicsF;

		container = View();

		objListView = ScrollView();
		objListView.canvas = View();
		this.pupulateObjectList(objListView.canvas);

		volSlider = Slider().orientation_(\horizontal);
		volSlider.action_({|s|
			OSCthulhu.setSyncArg("Trsq_Global", 1, s.value.asFloat);
		});

		globalControls = View();
		globalControls.layout_(
			VLayout(
				StaticText().string_("Volume"),
				volSlider
			)
		);

		argsLayout = HLayout();

		["h_ArgA", "h_ArgB"].do{|item|
			argsLayout.add(
				DragSource()
				.background_(TarasqueColorScheme.synthHandleActiveColor(item.split($_)[0]))
				.object_(item.asSymbol)
				.string_(item.split($_)[1])
				.dragLabel_(item.split($_)[1]),
				1
			);
		};

		globalControls.layout.add(argsLayout,1);

		dynamicsLayout = VLayout();

		dynamicsP = HLayout();

		["a_pp", "a_p", "a_mp"].do{|item|
			dynamicsP.add(
				DragSource()
				.background_(TarasqueColorScheme.synthHandleActiveColor(item.split($_)[0]))
				.object_(item.asSymbol)
				.string_(item.split($_)[1])
				.dragLabel_(item.split($_)[1]),
				1
			);
		};

		dynamicsLayout.add(dynamicsP,1);

		dynamicsF = HLayout();

		["a_mf", "a_f", "a_ff"].do{|item|
			dynamicsF.add(
				DragSource()
				.background_(TarasqueColorScheme.synthHandleActiveColor(item.split($_)[0]))
				.object_(item.asSymbol)
				.string_(item.split($_)[1])
				.dragLabel_(item.split($_)[1]),
				1
			);
		};

		dynamicsLayout.add(dynamicsF,1);

		globalControls.layout.add(dynamicsLayout,1);

		container.layout_(
			VLayout(
				StaticText().string_("Objects"),
				[objListView, stretch:2],
				StaticText().string_("Global Controls"),
				globalControls,
				[Button().states_([["Deselect All"]]).action_({ TarasqueDesktop.deselectAll; }),stretch:1]
			)
		);

		^[container,stretch:1];
	}

	pupulateObjectList
	{|objList|

		objList.removeAll;
		objList.layout_(VLayout());

		Tarasque.scoreObjects[Tarasque.scoreIndex].do{|item|
			objList.layout.add(
				DragSource()
				.background_(TarasqueColorScheme.synthHandleActiveColor(item.split($_)[0]))
				.object_(item.asSymbol)
				.string_(item.split($_)[1].replace("."," "))
				.dragLabel_(item.split($_)[1]),
				1
			);
		};

		objList.layout.add(nil);

		^objList;
	}

	free
	{
		osc.collect({|def| def.free; });
	}
}

TarasqueSelectionArea
{
	var origin, <area;

	*new
	{|origin|
		^super.newCopyArgs(origin).init;
	}

	init
	{
		area = Rect.fromPoints(origin,origin);
	}

	update
	{|extent|
		area = Rect.fromPoints(origin,extent);
	}

	draw
	{
		Pen.color = Color.new(0.8, 0.0, 0.3, 0.25);
		Pen.addRect(area);
		Pen.draw(3);
	}
}

/////////// DesktopObjects ///////////

/*
* Represents/displays remote user's mouse cursors on the desktop
*/
TarasqueCursor
{
	var <name, <>pos;

	*new
	{|name = "default", pos|
		^super.newCopyArgs(name,pos);
	}

	draw
	{
		var ang = pi*0.125;

		Pen.color = Color.gray(0.15);
		Pen.addAnnularWedge(Point(pos.x+1,pos.y+3),1,20,ang,pi*0.2);
		Pen.draw(3);

		Pen.color = Manticore.users.at(name).color;
		Pen.addAnnularWedge(pos,1,20,ang,pi*0.2);
		Pen.draw(3);

		Pen.color = Color.gray(0.2);
		Pen.stringAtPoint(name.asString,(pos+7@3).asPoint);
		Pen.color = Manticore.users.at(name).color;
		Pen.stringAtPoint(name.asString,(pos+6@2).asPoint);
	}
}

/*
* Superclass for all desktop object types
*/
TarasqueDesktopObject
{
	var <>origin;		// center point
	var <handleRad = 20;	// radius of the clickable area
	var <zoneRad = 200;	// radius of the influence area
	var <>selectedBy = "none";	// user who has this selected
	var <>clickOffset;		// used to handle dragging

	var <category;	// which category the object belongs to
	var <type;		// object specific name/type
	var <synth = nil;	// synth represented by the object
	var <inputBus;	// input busses used by the synth
	var <outputBus;	// output busses used by the synth
	var <controlConnections;	// reference for connections between ControlObjects and others

	*new
	{|t,pos|
		^super.new.init(t,pos);
	}

	init
	{|t,pos|
		origin = pos;
		category = t.asString.split($_)[0];
		type = t.asString.split($_)[1];
		inputBus = TarasqueServerItems.defaultBus;
	}

	free
	{

	}

	setX
	{|xPos|
		origin.x = xPos;
	}

	setY
	{|yPos|
		origin.y = yPos;
	}

	attemptConnection
	{|other|
		if(origin.dist(other.origin) <= other.zoneRad,{
			this.connect(other);
			},{
				this.disconnect(other);
		});
	}

	connect
	{|other|
		if(synth.notNil,{
			inputBus = other.outputBus;
			inputBus.setAll(0);
			synth.set("in",inputBus);
			//("synth:" + type + "\ninBus:" + inputBus).postln;
		});
	}

	disconnect
	{|other|
		if(synth.notNil && other.outputBus == inputBus,{
			inputBus = TarasqueServerItems.defaultBus;
			synth.set("in",inputBus);
		});
	}

	drawBottomLayer
	{
		Pen.addArc(origin,zoneRad,0,2pi);
		Pen.fillRadialGradient(
			origin,
			origin,
			0,
			zoneRad,
			TarasqueColorScheme.synthZoneCenterColor(category).alpha_(0.2),
			TarasqueColorScheme.synthZoneOuterColor(category).alpha_(0.2)
		);
	}

	drawTopLayer
	{
		/*
		if(inputBus != TarasqueServerItems.defaultBus,{
		Pen.color = TarasqueColorScheme.synthHandleActiveColor(category);
		},{
		Pen.color = TarasqueColorScheme.synthHandleInactiveColor(category);
		});
		*/
		Pen.addArc(
			origin,
			handleRad,
			0,
			2pi
		);
		//Pen.fill;
		if(inputBus != TarasqueServerItems.defaultBus,{
			Pen.fillRadialGradient(
				origin,
				origin,
				5,
				handleRad,
				TarasqueColorScheme.synthHandleActiveColor(category),
				TarasqueColorScheme.synthZoneOuterColor(category).alpha_(0)
			);
			},{
				Pen.fillRadialGradient(
					origin,
					origin,
					5,
					handleRad,
					TarasqueColorScheme.synthHandleInactiveColor(category),
					TarasqueColorScheme.synthZoneOuterColor(category).alpha_(0)
				);
		});
		/*
		Pen.color = TarasqueColorScheme.synthZoneCenterColor(category).alpha_(0.6);
		Pen.addAnnularWedge(
		origin,
		handleRad*0.7,
		handleRad,
		0,
		0.8pi
		);
		Pen.fill;

		Pen.color = TarasqueColorScheme.synthZoneOuterColor(category).alpha_(0.6);
		Pen.addAnnularWedge(
		origin,
		handleRad*0.4,
		handleRad*0.8,
		0.8pi,
		0.9pi
		);
		Pen.fill;

		Pen.color = TarasqueColorScheme.synthZoneCenterColor(category).alpha_(0.6);
		Pen.addAnnularWedge(
		origin,
		handleRad*0.2,
		handleRad*0.5,
		1.7pi,
		0.3pi
		);
		Pen.fill;
		*/
	}

	drawText
	{
		var xOffset, yOffset;

		xOffset = origin.x - (type.size/2 * 6);
		yOffset = origin.y - 6;

		Pen.color = Color.black;
		Pen.stringAtPoint(type.replace("."," "),Point(xOffset+1,yOffset+1));
		if(selectedBy != "none",
			{
				Pen.color = Manticore.users.at(selectedBy.asSymbol).color;
			},
			{
				Pen.color = Color.white;
		});
		Pen.stringAtPoint(type.replace("."," "),Point(xOffset,yOffset));
	}
}

/*
* Top level impulse generators
*/
TarasqueColotomyObject : TarasqueDesktopObject
{
	*new
	{|t,pos|
		^super.new.init(t,pos).setup;
	}

	setup
	{
		zoneRad = 200;
		outputBus = Bus.audio(Server.default,5);
		/*
		if(activeColotomy.at(type.asSymbol).isNil,{
		activeColotomy.put(
		type.asSymbol,
		Synth.tail(TarasqueServerItems.colotomyGroup,type,["out",outputBus])
		);
		});
		*/
		synth = Synth.tail(TarasqueServerItems.colotomyGroup,type,["out",outputBus]);	}

	connect
	{|other|

	}

	disconnect
	{|other|

	}

	free
	{
		synth.free;
		outputBus.free;
	}
}

/*
* Demand rate control generators
*/
TarasquePatternObject : TarasqueDesktopObject
{
	*new
	{|t,pos|
		^super.new.init(t,pos).setup;
	}

	setup
	{
		zoneRad = 75;
		outputBus = Bus.audio(Server.default,1);
		synth = Synth.tail(TarasqueServerItems.patternGroup,type,["out",outputBus,"in",TarasqueServerItems.defaultBus]);
	}

	free
	{
		synth.free;
		outputBus.free;
	}
}

/*
* Trigger based audio synths
*/
TarasqueRthymicObject : TarasqueDesktopObject
{
	*new
	{|t,pos|
		^super.new.init(t,pos).setup;
	}

	setup
	{
		zoneRad = 100;
		synth = Synth.tail(
			TarasqueServerItems.audioGroup,
			type,
			[
				"out",TarasqueServerItems.audioToEffectsBus,
				"in",TarasqueServerItems.defaultBus,
				"argA", rrand(0.0,1.0),
				"argB", rrand(0.0,1.0)
			]
		);
		controlConnections = Array.fill(2,{nil});
	}

	free
	{
		synth.release;
	}

	drawBottomLayer
	{
		super.drawBottomLayer;

		controlConnections.do{|slot, i|
			if(slot.notNil,{
				Pen.color = Color.black;
				Pen.line(origin,slot.value.origin);
				Pen.stroke;
			});
		};
	}

	attemptControl
	{|handle|
		controlConnections.do{|slot, i|
			if(slot.isNil,{	// try a new connection

				if(handle.origin.dist(origin) <= zoneRad,{ // connect if close enough

					switch(handle.type.asSymbol,
						\ArgA, {
							switch(i,
								0, { controlConnections[0] = Ref(handle) }
							);
						},
						\ArgB, {
							switch(i,
								1, { controlConnections[1] = Ref(handle) }
							);
						}
					);

				});
				},{	// handle an old connection

					if(handle.origin.dist(origin) <= zoneRad,{  // set values

						switch(i,
							0, { synth.set("argA", handle.origin.dist(origin).linlin(0.0,zoneRad,1.0,0.0) ); },
							1, { synth.set("argB", handle.origin.dist(origin).linlin(0.0,zoneRad,1.0,0.0) ); }
						);

						},{

							if(slot.value === handle,{  // else disconnect if it was connected
								controlConnections[i] = nil;
							});

					});
			});
		};
	}
}

/*
* Constant audio synths
*/
TarasqueDroneObject : TarasqueDesktopObject
{
	*new
	{|t,pos|
		^super.new.init(t,pos).setup;
	}

	setup
	{
		zoneRad = 100;
		synth = Synth.tail(
			TarasqueServerItems.audioGroup,
			type,
			[
				"out",TarasqueServerItems.audioToEffectsBus,
				"in",TarasqueServerItems.defaultBus,
				"argA", rrand(0.0,1.0),
				"argB", rrand(0.0,1.0)
			]
		);
		controlConnections = Array.fill(2,{nil});
	}

	free
	{
		synth.release;
	}

	drawBottomLayer
	{
		super.drawBottomLayer;

		controlConnections.do{|slot, i|
			if(slot.notNil,{
				Pen.color = Color.black;
				Pen.line(origin,slot.value.origin);
				Pen.stroke;
			});
		};
	}

	attemptControl
	{|handle|
		controlConnections.do{|slot, i|
			if(slot.isNil,{	// try a new connection

				if(handle.origin.dist(origin) <= zoneRad,{ // connect if close enough

					switch(handle.type.asSymbol,
						\ArgA, {
							switch(i,
								0, { controlConnections[0] = Ref(handle) }
							);
						},
						\ArgB, {
							switch(i,
								1, { controlConnections[1] = Ref(handle) }
							);
						}
					);

				});
				},{	// handle an old connection

					if(handle.origin.dist(origin) <= zoneRad,{  // set values

						switch(i,
							0, { synth.set("argA", handle.origin.dist(origin).linlin(0.0,zoneRad,1.0,0.0) ); },
							1, { synth.set("argB", handle.origin.dist(origin).linlin(0.0,zoneRad,1.0,0.0) ); }
						);

						},{

							if(slot.value === handle,{  // else disconnect if it was connected
								controlConnections[i] = nil;
							});

					});
			});
		};
	}
}

/*
* Control handles for parameter modulation
*/
TarasqueControlObject : TarasqueDesktopObject
{

	*new
	{|t,pos|
		^super.new.init(t,pos).setup;
	}

	setup
	{
		handleRad = 15;
	}

	drawBottomLayer
	{

	}

	drawTopLayer
	{
		Pen.addArc(
			origin,
			handleRad,
			0,
			2pi
		);
		Pen.fillRadialGradient(
			origin,
			origin,
			5,
			handleRad,
			TarasqueColorScheme.synthZoneCenterColor(category),
			TarasqueColorScheme.synthZoneOuterColor(category).alpha_(0)
		);
	}
}

TarasqueAreaObject : TarasqueDesktopObject
{
	var <argName,<argValue;

	*new
	{|t,pos,aName,aValue|
		^super.new.init(t,pos).setup;
	}

	setup
	{
		zoneRad = 150;
		switch(type.asSymbol,
			\pp, { argName = "vol"; argValue = 0.05; },
			\p, { argName = "vol"; argValue = 0.24; },
			\mp, { argName = "vol"; argValue = 0.43; },
			\mf, { argName = "vol"; argValue = 0.62; },
			\f, { argName = "vol"; argValue = 0.81; },
			\ff, { argName = "vol"; argValue = 1.0; },
		);
	}
}


/*

//MACRO FACTORY
// run after Tarasque is up!

(
// set this to be the center point
~mouse = 0@0;

OSCthulhu.onAddSyncObject(\test, {|msg|
[ "\"" ++ msg[3] ++ "\"", (Point(msg[4], msg[5]) - ~mouse)].postln;
});
)


[
// posts go here
].do{|entry, i|
[ entry[0], Point( 341, 246 ) + entry[1]].postln;	// change the Point to be the center
};
)
*/

/////////////////
TarasqueII
{
	var audioServer;

	*new
	{
		^super.new().init();
	}

	*initClass
	{
		ManticoreII.registerPiece('Tarasque');	// register Tarasque with Manticore
	}

	init
	{
		Manticore.group = "Trsq";

		Tarasque.storeSynthDefs;

		OSCthulhu.changePorts(57120);
		OSCthulhu.login(Manticore.group);

		OSCthulhu.addSyncObject(
			"Trsq_Global",
			Manticore.group,
			"glbl",
			[
				0,		// scoreIndex
				1.0		// volume
			]
		);
	}

}