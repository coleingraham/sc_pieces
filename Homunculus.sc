/*
*
*/
Homunculus : ManticorePiece
{
	var osc;
	var desktopView, objectView;

	classvar <scoreIndex, <scoreObjects;
	var scoreText;

	*new
	{
		^super.new.init;
	}

	*initClass
	{
		Manticore.registerPiece('Homunculus');	// register Tarasque with Manticore
	}

	init
	{
		Manticore.group = "HM";

		osc = List.new;
		scoreIndex = 0;

		scoreText = [

			"slowly add 500Crackers x4\n" ++
			"then slowly add ExoticGarlicSyrup x3\n" ++
			"then add RoutineVaccination x2",

			"keep ExoticGarlicSyrup x3 + RoutineVaccination x2\n" ++
			"PinkSpagetti x4",

			"keep PinkSpagetti x4\n" ++
			"DowningBicardi x2 + IndustrialWindmills x2",

			"500Crackers x2 + ExoticGarlicSyrup x2",

			"gradually kill others\n" ++
			"BigPotatoMoths x2 + MentalKitty x2\n" ++
			"then InverseCosine x2 or 3",

			"gradually kill MentalKitty and InverseCosine\n" ++
			"add NinjaGloves x3\n" ++
			"later, kill BigPotatoMoths",

			"gradually kill NinjaGloves\n" ++
			"CrazySeagulls x1 (bottom right), ExoticGarlicSyrup x2, 500Crackers\n" ++
			"later slowly add more CrazySeagulls",

			"remove all but CrazySeagulls\n" ++
			"gradually add tons of CrazySeagulls (bottom right) + HappyTree + BreakTheMirrors (bottom left)\n" ++
			"slowly move BreakTheMirrors to top right",

			"add PartyHats x5 or 6 (middle)\n" ++
			"kill everything but PartyHats and HappyTree\n" ++
			"move PartyHats to top right, kill HappyTree, then slowls down to bottom\n" ++
			"kill all\n" ++
			"-End-"
		];

		scoreObjects = [
			[
				"s_500Crackers",
				"s_ExoticGarlicSyrup",
				"s_RoutineVaccination"

			],
			[
				"s_PinkSpagetti",
				"s_ExoticGarlicSyrup",
				"s_RoutineVaccination"

			],
			[
				"s_PinkSpagetti",
				"s_DowningBicardi",
				"s_IndustrialWindmills"

			],
			[
				"s_500Crackers",
				"s_ExoticGarlicSyrup",
				"s_OrangePeanut",
				"s_FingerTime"
			],
			[
				"s_BigPotatoMoths",
				"s_MentalKitty",
				"s_InverseCosine",
				"s_StopDoingHeroin"

			],
			[
				"s_BigPotatoMoths",
				"s_NinjaGloves"
			],
			[
				"s_CrazySeagulls",
				"s_OrangePeanut",
				"s_FingerTime"
			]
			,
			[
				"s_CrazySeagulls",
				"s_BreakTheMirrors",
				"s_HappyTree"
			],
			[
				"s_PartyHats",
				"s_HappyTree"
			]
		];

		this.setupOSClisteners;
		this.storeSynthDefs;

		OSCthulhu.changePorts(57120);
		OSCthulhu.login(Manticore.group);

		// make the score buttons work
		Manticore.onScoreNext({
			OSCthulhu.setSyncArg("HM_Global", 0, (scoreIndex+1)%scoreText.size);
		});
		Manticore.onScorePrevious({
			OSCthulhu.setSyncArg("HM_Global", 0, (scoreIndex-1)%scoreText.size);
		});

		// args: scoreIndex, volume
		OSCthulhu.addSyncObject(
			"HM_Global",
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
							//TarasqueServerItems.changeEffects(scoreEffects[argValue].asString);
						});

						scoreIndex = argValue;
						Manticore.updateScore(scoreText[scoreIndex]);

						HomunculusServerItems.effectSynths.do{|synth|
							synth.set("sideAmp",argValue.linexp(0,scoreText.size-1,0.0001,1.0));
						};

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
	}

	getInterface
	{|p|
		desktopView = HomunculusDesktop.new;
		objectView = HomunculusObjectWindow.new;

		^p.layout_(HLayout( desktopView.getView, objectView.getView ));
	}

	storeSynthDefs
	{
		this.feedbackSynthWrap("BigPotatoMoths",{|argA=0.0, argB=0.0, bufnum=0|
			var sig, env, cheby;

			sig = PlayBuf.ar(
				1,
				bufnum,
				LFNoise0.kr(Rand(0.5,2)).range(0.125,3),
				trigger: Dust.kr(Rand(0.5,2)),
				loop:1,
				startPos: LFNoise0.kr(Rand(0.5,2)).range(0,BufFrames.ir(bufnum))
			);

			sig = LeakDC.ar(sig);

			sig = Latch.ar(
				sig,
				PulseDivider.ar(
					Impulse.ar(
						DelayN.ar(
							ZeroCrossing.ar(sig) * argB.linlin(0.0,1.0,3.0,0.33),
							0.1,
							argA.linlin(0.0,1.0,0.1,0.001)
						)
					),
					argB.linlin(0.0,1.0,2,9).round
				)
			);

			cheby = LocalBuf(512).set( Signal.chebyFill(513,[0.25,0.5,0.25]) );

			sig = sig - SinOsc.ar(
				Rand(55,220)*argB.linlin(0.0,1.0,0.25,4),
				Latch.ar(
					sig,
					PulseDivider.ar(
						Impulse.ar(
							DelayN.ar(
								ZeroCrossing.ar(sig) * argA.linlin(0.0,1.0,3.0,0.33),
								0.1,
								argB.linlin(0.0,1.0,0.1,0.001)
							)
						),
						argA.linlin(0.0,1.0,2,9).round
					)
				).range(-16pi,16pi),
				0.3
			);

			sig = FreqShift.ar(
				Shaper.ar(cheby,sig),
				LFNoise1.kr(LFNoise0.kr(Rand(1,4)).range(1,3)).range(-70,70)
			);

			sig = LPF.ar(
				Decay.ar(sig,0.005),
				LFNoise0.kr(LFNoise0.kr(Rand(0.5,2)).range(5,10)).range(2000,7000)*argB.linlin(0.0,1.0,0.25,4)
			);

			sig = HPF.ar(
				sig,
				LFNoise0.kr(LFNoise0.kr(Rand(0.5,2)).range(5,10)).range(20,1000)*argA.linlin(0.0,1.0,0.25,4)
			);

			sig = BufCombC.ar(
				bufnum,
				Shaper.ar(cheby,sig),
				LFNoise2.kr(LFNoise0.kr(Rand(1,6)).range(3,5)).range(0.05,0.25),
				0.5,
				0.9
			);
		});

		this.feedbackSynthWrap("MentalKitty",{|argA=0.0, argB=0.0, bufnum=0|
			var sig, env, cheby;

			sig = PlayBuf.ar(
				1,
				bufnum,
				LFNoise2.kr(LFNoise0.kr(Rand(1,6)*argA.linlin(0.0,1.0,0.25,1.75)).range(3,9)).range(-0.125,-3),
				loop:1
			);

			sig = LeakDC.ar(sig);

			sig = Latch.ar(
				sig,
				PulseDivider.ar(
					Impulse.ar(
						DelayN.ar(
							ZeroCrossing.ar(sig) * argA.linlin(0.0,1.0,3.0,0.33),
							0.1,
							argA.linlin(0.0,1.0,0.1,0.001)
						)
					),
					argB.linlin(0.0,1.0,2,9).round
				)
			) * 0.75;

			cheby = LocalBuf(512).set( Signal.chebyFill(513,[0.25,0.5,0.25]) );

			sig = Shaper.ar(cheby,(sig + 0.001 * argA.linlin(0.0,1.0,-3.0,-1.0)));

			sig = FreqShift.ar(sig, argA.linlin(0.0,1.0,-600,-200));

			sig = FreeVerb.ar(
				Latch.ar(
					sig,
					PulseDivider.ar(
						Impulse.ar(
							DelayN.ar(
								ZeroCrossing.ar(sig) * argA.linlin(0.0,1.0,0.33,3.0),
								0.1,
								argB.linlin(0.0,1.0,0.1,0.001)
							)
						),
						argB.linlin(0.0,1.0,2,9).round
					)
				),
				0.3,
				0.4,
				0.5
			);

			sig = DFM1.ar(sig, LFNoise0.kr(Rand(5.0,8.0)).range(2000,6000)*argB.linlin(0.0,1.0,0.25,1.75), argA.linlin(0.0,1.0,0.75,2.0)) * 8;

			sig = BufAllpassC.ar(
				bufnum,
				Shaper.ar(cheby,sig),
				0.25,
				1,
				argB.linlin(0.0,1.0,0.8,0.99)
			);
		});

		this.feedbackSynthWrap("ExoticGarlicSyrup",{|argA=0.0, argB=0.0, bufnum=0|
			var sig, env, cheby;

			sig = PlayBuf.ar(
				1,
				bufnum,
				LFNoise0.kr(LFNoise0.kr(Rand(1,6)*argB.linlin(0.0,1.0,0.125,1.875)).range(2,8)).range(-3,3),
				loop:1
			);

			sig = LeakDC.ar(sig);

			sig = Latch.ar(
				sig,
				PulseDivider.ar(
					Impulse.ar(
						DelayN.ar(
							ZeroCrossing.ar(sig) * argB.linlin(0.0,1.0,4,0.25),
							0.1,
							argA.linlin(0.0,1.0,0.001,0.1)
						)
					),
					argB.linlin(0.0,1.0,5,1).round
				)
			);

			cheby = LocalBuf(512).set( Signal.chebyFill(513,[0.25,0.5,0.25]) );

			sig = VOSIM.ar(
				sig,
				LFNoise0.kr(LFNoise0.kr(Rand(0.5,2)).range(0.5,7)).range(20,10000) * argA.linlin(0.0,1.0,0.125,1.875),
				sig.range(1,6),
				0.99
			);

			sig = FreqShift.ar(sig, argA.linlin(0.0,1.0,-100,100));

			sig = BufCombN.ar(
				bufnum,
				Shaper.ar(cheby,sig),
				LFNoise0.kr(LFNoise0.kr(Rand(1,6)).range(1,4)).range(0.08,0.25),
				0.5,
				argA.linlin(0.0,1.0,0.8,0.99)					);
		});

		this.feedbackSynthWrap("RoutineVaccination",{|argA=0.0, argB=0.0, bufnum=0|
			var sig, cheby;

			sig = PlayBuf.ar(
				1,
				bufnum,
				LFNoise1.kr(LFNoise0.kr(Rand(1,6)).range(2,8)).range(-0.1,-2),
				loop:1
			);

			sig = LeakDC.ar(sig);

			cheby = LocalBuf(512).set( Signal.chebyFill(513,[0.3, -0.8, 1.1]) );

			sig = KmeansToBPSet1.ar(
				Latch.ar(
					sig,
					Impulse.ar(DelayN.ar(ZeroCrossing.ar(sig), 0.1, argB.linlin(0.0,1.0,0.1,0.001)))
				).range(0,3000)*argB.linlin(0.0,1.0,0.125,1.875),
				tnewdata: sig
			);

			sig = sig * Decay.kr(
				Impulse.kr(
					LFNoise0.kr(Rand(0.125,0.25)*argB.linlin(0.0,1.0,0.125,1.875)).range(2,7),
					LFNoise1.kr(Rand(1,4)*argA.linlin(0.0,1.0,0.125,1.875)).range(0.2,1)
				).range(0,1),
				0.6
			);

			sig = RLPF.ar(
				Shaper.ar(cheby,sig),
				LFNoise1.kr(LFNoise0.kr(Rand(1,6)).range(2,19)).range(0,2500)*argA.linlin(0.0,1.0,0.125,1.875),
				LFNoise1.kr(LFNoise0.kr(Rand(1,6)).range(2,6)).range(0.2,1.0)*argB.linlin(0.0,1.0,0.125,1.875)
			);

			sig = FreqShift.ar(sig, Rand(-500,500)*argA.linlin(0.0,1.0,0.125,1.875));

			sig = BufCombL.ar(
				bufnum,
				Shaper.ar(cheby,sig),
				LFNoise0.kr(LFNoise0.kr(Rand(1,6)).range(0.5,2)).range(0.05,0.25),
				0.35,
				0.9
			);
		});

		this.feedbackSynthWrap("DowningBicardi",{|argA=0.0, argB=0.0, bufnum=0|
			var sig, cheby;

			sig = PlayBuf.ar(
				1,
				bufnum,
				LFNoise2.kr(LFNoise0.kr(Rand(1,6)).range(2,8)).range(-0.1,-2),
				loop:1
			);

			sig = LeakDC.ar(sig * argA.linlin(0.0,1.0,1.0,4.0));

			cheby = LocalBuf(512).set( Signal.chebyFill(513,[0.3, -0.8, 1.1]) );

			sig = Latch.ar(
				sig,
				Impulse.ar(
					DelayN.ar(
						ZeroCrossing.ar(sig) * argB.linlin(0.0,1.0,0.5,2.0),
						0.1,
						argA.linlin(0.0,1.0,0.001,0.1)
					)
				)
			) + LFNoise0.kr(0.3);

			sig = FreqShift.ar(sig, argA.linlin(0.0,1.0,-1500.0,0.0));

			sig = Convolution.ar(DelayN.ar(sig,0.25,LFNoise0.kr(Rand(1,10)).range(0.01,0.25)), bufnum, 512);

			sig = FreqShift.ar(sig, LFNoise0.kr(LFNoise0.kr(Rand(1,6)).range(20,80))*argB.linlin(0.0,1.0,0.0,1500.0));

			sig = BufAllpassC.ar(
				bufnum,
				Shaper.ar(cheby,sig * argB.linlin(0.0,1.0,4.0,1.0)),
				0.25,
				3,
				0.9
			);
		});

		this.feedbackSynthWrap("InverseCosine",{|argA=0.0, argB=0.0, bufnum=0|
			var sig, cheby;

			sig = PlayBuf.ar(
				1,
				bufnum,
				LFNoise2.kr(LFNoise0.kr(Rand(1,6)).range(2,8)).range(-0.1,-2),
				loop:1
			);

			sig = LeakDC.ar(sig);

			cheby = LocalBuf(512).set( Signal.chebyFill(513,[0.25,0.5,0.25]) );

			sig = Latch.ar(
				sig,
				Impulse.ar(
					DelayN.ar(
						ZeroCrossing.ar(sig) * argA.linlin(0.0,1.0,0.25,4.0),
						0.1,
						argB.linlin(0.0,1.0,0.1,0.001)
					)
				)
			) + LFNoise0.kr(0.3);

			sig = Convolution.ar(BufDelayC.ar(sig,LFNoise0.kr(2).range(0.01,0.25)), bufnum, 512);

			sig = FreqShift.ar(
				sig,
				DelayN.ar(
					sig,
					0.1,
					argB.linlin(0.0,1.0,0.001,0.1)
				).range(-100,100)
			);

			sig = FreqShift.ar(sig,argA.linlin(0.0,1.0,-1500,-3000));

			sig = BufAllpassC.ar(
				bufnum,
				Shaper.ar(cheby,sig),
				0.25,
				3,
				0.9
			);
		});

		this.feedbackSynthWrap("IndustrialWindmills",{|argA=0.0, argB=0.0, bufnum=0|
			var sig, cheby;

			sig = PlayBuf.ar(
				1,
				bufnum,
				LFNoise2.kr(LFNoise0.kr(Rand(1,6)).range(2,8)).range(-8.0,-4.0),
				loop:1
			);

			sig = LeakDC.ar(sig);

			sig = Latch.ar(
				sig,
				PulseDivider.ar(
					Impulse.ar(
						DelayN.ar(
							ZeroCrossing.ar(sig) * argA.linlin(0.0,1.0,0.25,4.0),
							0.1,
							argB.linlin(0.0,1.0,0.1,0.001)
						)
					),
					argA.linlin(0.0,1.0,1,7).round
				)
			);

			cheby = LocalBuf(512).set( Signal.chebyFill(513,[0.3, -0.8, 1.1]) );

			sig = DFM1.ar(
				sig,
				freq: SinOsc.kr(LFNoise0.kr(Rand(2,5)*argB.linlin(0.0,1.0,1.0,3.0)).range(0.01,0.1)).range(55.0,110.0)*argA.linlin(0.0,1.0,1.0,3.0),
				res: LFNoise2.kr(LFNoise0.kr(Rand(2,5)*argA.linlin(0.0,1.0,1.0,3.0)).range(0.2,0.6)).range(0.1,3.0),
				inputgain: LFNoise2.kr(LFNoise0.kr(Rand(2,5)*argA.linlin(0.0,1.0,1.0,3.0)).range(0.2,0.6)).range(1.0,2.0),
				type: LFPulse.kr(LFNoise0.kr(Rand(1,4)*argB.linlin(0.0,1.0,1.0,3.0)).range(0.2,1.1), 0, LFNoise0.kr(Rand(1,4)).range(0.1,0.5)).range(0,1),
				noiselevel: LFNoise2.kr(LFNoise0.kr(Rand(2,5)).range(0.2,0.6)).range(0.0003,0.0005)*argB.linlin(0.0,1.0,1.0,3.0)
			);

			sig = FreqShift.ar(
				sig,
				DelayN.ar(
					Shaper.ar(cheby,sig),
					0.3,
					argA.linlin(0.0,1.0,0.0001,0.3)
				).range(-50,200)
			);

			sig = LPF.ar(sig, LFNoise1.kr(LFNoise0.kr(Rand(2,5)*argB.linlin(0.0,1.0,1.0,6.0)).range(0.3,0.7)).range(2000,4000));

			sig = BufCombN.ar(
				bufnum,
				Shaper.ar(cheby,sig * argB.linlin(0.0,1.0,1.0,5.0)),
				LFNoise0.kr(LFNoise0.kr(Rand(2,5)*argB.linlin(0.0,1.0,1.0,3.0)).range(0.3,0.7)).range(0.1,HomunculusServerItems.bufferLength),
				LFNoise0.kr(LFNoise0.kr(Rand(2,5)*argA.linlin(0.0,1.0,1.0,3.0)).range(0.3,0.7)).range(HomunculusServerItems.bufferLength,1.0),
				0.9
			);
		});

		this.feedbackSynthWrap("PinkSpagetti",{|argA=0.0, argB=0.0, bufnum=0|
			var sig, cheby;

			sig = PlayBuf.ar(
				1,
				bufnum,
				LFNoise2.kr(LFNoise0.kr(Rand(1,6)).range(2,8)).range(-2.0,-0.7),
				loop:1
			);

			sig = LeakDC.ar(sig);

			cheby = LocalBuf(512).set( Signal.chebyFill(513,[0.5,0.25,0.25,0.3]) );

			sig = Latch.ar(
				sig,
				Impulse.ar(
					DelayN.ar(
						ZeroCrossing.ar(sig) * argA.linlin(0.0,1.0,0.125,0.5),
						0.1,
						argB.linlin(0.0,1.0,0.001,0.1)
					)
				)
			) + LFNoise0.kr(0.1);

			sig = Ringz.ar(
				sig,
				ZeroCrossing.ar(
					Latch.ar(
						sig,
						Impulse.ar(
							DelayN.ar(
								ZeroCrossing.ar(sig) * argA.linlin(0.0,1.0,0.0125,0.05),
								0.1,
								argB.linlin(0.0,1.0,0.001,0.1)
							)
						)
					)
				) * 0.7,
				0.4
			);

			sig = DFM1.ar( sig, argA.linlin(0.0, 1.0, 50.0, 700.0), 0.75, type:1);

			sig = MoogLadder.ar(sig,argB.linlin(0.0,1.0,3000,7000),0.75);

			sig = BufCombN.ar(
				bufnum,
				Shaper.ar(cheby,sig*4),
				0.25,
				1,
				0.9
			);

			sig = sig * LFPulse.ar(Rand(8.3,9.0), 0, LFNoise2.kr(Rand(0.5,2)).range(0.2,0.6)).range(0.0,1.0);
		});

		this.feedbackSynthWrap("500Crackers",{|argA=0.0, argB=0.0, bufnum=0|
			var sig, cheby;

			sig = PlayBuf.ar(
				1,
				bufnum,
				LFNoise2.kr(LFNoise0.kr(Rand(1,6)).range(2,8)).range(-2.0,-0.7),
				loop:1
			);

			sig = LeakDC.ar(sig);

			cheby = LocalBuf(512).set( Signal.chebyFill(513,[0.3,0.5,0.25,0.6]) );

			sig = Latch.ar(
				sig,
				Impulse.ar(
					DelayN.ar(
						ZeroCrossing.ar(sig) * argA.linlin(0.0,1.0,0.125,0.5),
						0.1,
						argB.linlin(0.0,1.0,0.001,0.1)
					)
				)
			) + LFNoise0.kr(0.1);

			sig = PitchShift.ar(sig, 1.0, 0.9);

			sig = LPF.ar(sig, 500);

			sig = BufCombN.ar(
				bufnum,
				Shaper.ar(cheby,sig),
				0.25,
				1,
				0.9
			);

			sig = sig * LFPulse.ar(Rand(8.0,8.5), 0, LFNoise2.kr(Rand(0.5,2)).range(0.2,0.6)).range(0.0,1.0);
		});

		this.feedbackSynthWrap("NinjaGloves",{|argA=0.0, argB=0.0, bufnum=0|
			var sig, cheby;

			sig = PlayBuf.ar(
				1,
				bufnum,
				LFNoise2.kr(LFNoise0.kr(Rand(6,16)).range(2,8)).range(-0.65,0.65),
				loop:1
			);

			sig = LeakDC.ar(sig) + LFNoise0.kr(0.1);

			cheby = LocalBuf(512).set( Signal.chebyFill(513,[0.5,0.3,0.6,0.25]) );

			sig = Decimator.ar(
				DelayN.ar(sig,0.1,argA.linlin(0.0,1.0,0.001,0.1)),
				sig.range(SampleRate.ir * 0.25,SampleRate.ir),
				argB.linlin(0.0,1.0,2,32)
			);

			sig = BufCombL.ar(
				bufnum,
				Shaper.ar(cheby,sig),
				0.25,
				1,
				0.95
			);
		});

		this.feedbackSynthWrap("CrazySeagulls",{|argA=0.0, argB=0.0, bufnum=0|
			var sig, cheby;

			sig = PlayBuf.ar(
				1,
				bufnum,
				LFNoise2.kr(LFNoise0.kr(argA.linlin(0.0,1.0,2,10)).range(2,8)).range(-1.65,1.65),
				loop:1
			);

			sig = LeakDC.ar(sig);

			cheby = LocalBuf(512).set( Signal.chebyFill(513,[0.6,0.3,0.7,0.2]) );

			sig = Latch.ar(
				sig,
				Impulse.ar(
					DelayN.ar(
						ZeroCrossing.ar(sig) * argA.linlin(0.0,1.0,0.0125,0.05),
						0.1,
						argB.linlin(0.0,1.0,0.001,0.1)
					)
				)
			) + LFNoise0.kr(0.1);

			sig = SinOsc.ar(
				ZeroCrossing.ar([
					sig,
					DelayN.ar(
						FreqShift.ar(sig,argA.linlin(0.0,1.0,-30.0,-1.0)),
						0.3,argB.linlin(0.0,1.0,0.3,0.001)
					)
				]).round(55),
				ZeroCrossing.ar(sig).range(0.0,1000.0,-1.5pi,1.5pi)
			);

			sig = Mix.new(sig);

			sig = RLPF.ar(sig,ZeroCrossing.ar(sig)*0.5,argB.linlin(0.0,1.0,0.3,0.001));

			sig = BufCombN.ar(
				bufnum,
				Shaper.ar(cheby,sig.distort),
				0.25,
				2,
				0.9
			);
		});

		this.feedbackSynthWrap("PartyHats",{|argA=0.0, argB=0.0, bufnum=0|
			var sig, cheby;

			sig = PlayBuf.ar(
				1,
				bufnum,
				LFNoise2.kr(LFNoise0.kr(Rand(1,6)).range(2,8)).range(-0.1,-2),
				loop:1
			);

			sig = LeakDC.ar(sig);

			cheby = LocalBuf(512).set( Signal.chebyFill(513,[0.25,0.5,0.25]) );

			sig = Saw.ar(
				55 * (( argA.linlin(0.0,1.0,13,27).round - PulseCount.ar(
					Impulse.ar(ZeroCrossing.ar(sig)*0.0125),
					Impulse.ar(argA.linlin(0.0,1.0,0.25,2.0) + LFNoise0.kr(Rand(0.3,0.5)).range(0.1,0.65))
				)) % argA.linlin(0.0,1.0,13,27).round)/*,
				LFNoise0.kr(argB.linexp(0.0,1.0,0.5,6)).range(0.05,0.5)*/
			);

			sig = RLPF.ar(
				sig,
				55 * PulseCount.ar(
					Impulse.ar(ZeroCrossing.ar(sig)*0.013),
					Impulse.ar(argB.linlin(0.0,1.0,0.25,2.0) + LFNoise0.kr(Rand(0.3,0.5)).range(0.1,0.65))
				),
				argB.linlin(0.0,1.0,0.001,0.1)
			);

			sig = BufAllpassC.ar(
				bufnum,
				Shaper.ar(cheby,sig),
				0.25,
				3,
				0.9
			);

			sig = sig * LFPulse.ar(Rand(8.0,10.0), 0, LFNoise2.kr(Rand(0.5,2)).range(0.6,0.9)).range(0.0,1.0);
		});

		this.feedbackSynthWrap("BreakTheMirrors",{|argA=0.0, argB=0.0, bufnum=0|
			var sig, cheby;

			sig = PlayBuf.ar(
				1,
				bufnum,
				LFNoise2.kr(LFNoise0.kr(Rand(1,6)).range(2,8)).range(-0.1,-2),
				loop:1
			);

			sig = LeakDC.ar(sig);

			cheby = LocalBuf(512).set( Signal.chebyFill(513,[0.25,0.5,0.25]) );

			sig = Blip.ar(
				(7/8) * 110 * (( argA.linlin(0.0,1.0,13,27).round - PulseCount.ar(
					Impulse.ar(ZeroCrossing.ar(sig)*0.0125),
					Impulse.ar(argA.linlin(0.0,1.0,0.25,2.0) + LFNoise0.kr(Rand(0.3,0.5)).range(0.1,0.65))
				)) % argA.linlin(0.0,1.0,13,27).round),
				PulseCount.ar(
					Impulse.ar(ZeroCrossing.ar(sig)*0.0125),
					Impulse.ar(argB.linlin(0.0,1.0,0.25,2.0) + LFNoise0.kr(Rand(0.3,0.5)).range(0.1,0.65))
				)
			);

			sig = RLPF.ar(
				sig,
				(7/8) * 55 * PulseCount.ar(
					Impulse.ar(ZeroCrossing.ar(sig)*0.013),
					Impulse.ar(argB.linlin(0.0,1.0,0.25,2.0) + LFNoise0.kr(Rand(0.3,0.5)).range(0.1,0.65))
				),
				argB.linlin(0.0,1.0,0.001,0.1)
			);

			sig = BufAllpassC.ar(
				bufnum,
				Shaper.ar(cheby,sig),
				0.25,
				1,
				0.9
			);
		});

		this.feedbackSynthWrap("HappyTree",{|argA=0.0, argB=0.0, bufnum=0|
			var sig, cheby;

			sig = PlayBuf.ar(
				1,
				bufnum,
				LFNoise2.kr(LFNoise0.kr(Rand(1,6)).range(2,8)).range(-2.0,-0.7),
				loop:1
			);

			sig = LeakDC.ar(sig) + LFNoise0.kr(0.4);

			cheby = LocalBuf(512).set( Signal.chebyFill(513,[0.5,0.25,0.25,0.3]) );

			sig = PulseCount.ar(
				Impulse.ar(ZeroCrossing.ar(sig)),
				Impulse.ar(ZeroCrossing.ar(sig) * argA.linlin(0.0,1.0,0.125,0.5))
			) >> 1;

			sig = Lag.ar(sig,0.001);

			sig = BufCombN.ar(
				bufnum,
				Shaper.ar(cheby,sig),
				0.25,
				1,
				0.9
			);
		});

		this.feedbackSynthWrap("OrangePeanut",{|argA=0.0, argB=0.0, bufnum=0|
			var sig;

			sig = PlayBuf.ar(
				1,
				bufnum,
				LFNoise0.kr(LFNoise0.kr(Rand(1,6)).range(2,8)).range(2.0,8.0),
				loop:1
			) * LFNoise2.kr(LFNoise0.kr(Rand(1,6)).range(2,8)).range(0.5,2.0);

			sig = LeakDC.ar(sig);

			sig = BufCombN.ar(
				bufnum,
				sig,
				argA.linlin(0.0,1.0,HomunculusServerItems.bufferLength*0.125,HomunculusServerItems.bufferLength),
				1,
				argB.linlin(0.0,1.0,0.25,2.0)
			);
		});

		this.feedbackSynthWrap("FingerTime",{|argA=0.0, argB=0.0, bufnum=0|
			var sig;

			sig = PlayBuf.ar(
				1,
				bufnum,
				LFNoise1.kr(LFNoise0.kr(Rand(1,6)).range(2,8)).range(-2.0,-8.0),
				loop:1
			) * LFNoise0.kr(LFNoise0.kr(Rand(1,6)).range(2,8)).range(0.5,2.0);

			sig = LeakDC.ar(sig);

			sig = BufAllpassN.ar(
				bufnum,
				sig,
				argB.linlin(0.0,1.0,HomunculusServerItems.bufferLength*0.125,HomunculusServerItems.bufferLength),
				1,
				argA.linlin(0.0,1.0,2.0,0.25)
			);
		});

		this.feedbackSynthWrap("StopDoingHeroin",{|argA=0.0, argB=0.0, bufnum=0|
			var sig;

			sig = PlayBuf.ar(
				1,
				bufnum,
				argA.linlin(0.0,1.0,1.0,2.0),
				loop:1
			) * Pulse.kr(argA.linlin(0.0,1.0,0.125,2.5),argB.linlin(0.0,1.0,0.05,0.5)).range(0.125,1.0);

			sig = sig + Resonz.ar(LFSaw.ar(13.75,0,0.4),440,0.2);

			sig = LeakDC.ar(sig);

			sig = BufAllpassN.ar(
				bufnum,
				sig,
				HomunculusServerItems.bufferLength,
				1,
				0.9
			);
		});

		// effects
		(
			SynthDef("HomunculousFX",{|in=0,out=0, sideAmp=0.0|
				var sig, cheby, sidechain, low, hi, original;

				sig = In.ar(in,1);

				sig = HPF.ar(sig,20);

				sig = Median.ar(3, sig);

				original = sig;

				sidechain = Decay.ar(Impulse.ar(2.25),0.01);

				low = RLPF.ar(sig,Lag.kr(LFSaw.kr(2.25 * 0.125).range(10000,55),0.15),0.1) * 0.5;

				hi = RHPF.ar(sig,Lag.kr(LFSaw.kr(2.25 * 0.125).range(10000,55),0.15),0.1);

				sig = ((sig - low - hi) * sideAmp).distort;

				sig = Compander.ar(
					in: sig,
					control: sidechain,
					thresh: -72.dbamp,
					slopeBelow: 1.0,
					slopeAbove: 0.5,
					clampTime: 0.15,
					relaxTime: 0.125
				);

				sig = original + sig;

				sig = GVerb.ar(
					sig,
					roomsize: 22,
					revtime: 0.4,
					damping: 0.13,
					inputbw: 0.9,
					spread: 15,
					drylevel: -5.dbamp,
					earlyreflevel: -26.dbamp,
					taillevel: -17.dbamp
				);

				Out.ar(out,sig[0]);
			}).store;
		);
	}

	feedbackSynthWrap
	{|name,function|
		switch(Manticore.speakerConfiguration,
			\stereo, {
				(
					SynthDef(name,{|bufnum=0, out=0, pan=0, amp=0.5, gate=1, argA=0, argB=0|
						var sig, env, panX, panY, panZ;

						env = EnvGen.kr(Env.asr(1,1,1),gate,doneAction:2);

						panX = LFNoise2.kr(LFNoise0.kr(Rand(0.2,2).range(0.05,0.2)));
						panY = LFNoise2.kr(LFNoise0.kr(Rand(0.2,2).range(0.05,0.2)));
						panZ = LFNoise2.kr(LFNoise0.kr(Rand(0.2,2).range(0.05,0.2)));

						argA = Lag.kr(argA,0.5);
						argB = Lag.kr(argB,0.5);

						sig = SynthDef.wrap(function, nil, [argA, argB, bufnum]);

						sig = LeakDC.ar(sig*env);

						Out.ar(out,Pan2.ar(sig,panX,Lag.kr(amp,1)));
					}).store;
				);
			},
			\quad, {
				(
					SynthDef(name,{|bufnum=0, out=0, pan=0, amp=0.5, gate=1, argA=0, argB=0|
						var sig, env, panX, panY, panZ;

						env = EnvGen.kr(Env.asr(1,1,1),gate,doneAction:2);

						panX = LFNoise2.kr(LFNoise0.kr(Rand(0.2,2).range(0.05,0.2)));
						panY = LFNoise2.kr(LFNoise0.kr(Rand(0.2,2).range(0.05,0.2)));
						panZ = LFNoise2.kr(LFNoise0.kr(Rand(0.2,2).range(0.05,0.2)));

						argA = Lag.kr(argA,0.5);
						argB = Lag.kr(argB,0.5);

						sig = SynthDef.wrap(function, nil, [argA, argB, bufnum]);

						sig = LeakDC.ar(sig*env);

						Out.ar(out,Pan4.ar(sig,panX,panY,Lag.kr(amp,1)));
					}).store;
				);
			},
			\cube, {
				(
					SynthDef(name,{|bufnum=0, out=0, pan=0, amp=0.5, gate=1, argA=0, argB=0|
						var sig, env, panX, panY, panZ;

						env = EnvGen.kr(Env.asr(1,1,1),gate,doneAction:2);

						panX = LFNoise2.kr(LFNoise0.kr(Rand(0.2,2).range(0.05,0.2)));
						panY = LFNoise2.kr(LFNoise0.kr(Rand(0.2,2).range(0.05,0.2)));
						panZ = LFNoise2.kr(LFNoise0.kr(Rand(0.2,2).range(0.05,0.2)));

						argA = Lag.kr(argA,0.5);
						argB = Lag.kr(argB,0.5);

						sig = SynthDef.wrap(function, nil, [argA, argB, bufnum]);

						sig = LeakDC.ar(sig*env);

						Out.ar(out,CubePan.ar(sig,panX,panY,panZ,Lag.kr(amp,1)));
					}).store;
				);
			}
		);
	}

}

HomunculusServerItems
{
	classvar <>currentVolume;
	classvar <feedbackBuffer;
	classvar <feedbackGroup;
	classvar <effectGroup;
	classvar <audioToEffectsBus;
	classvar <bufferLength = 1.0;//0.25;
	classvar <effectSynths;

	*init
	{
		currentVolume = 1.0;
		feedbackBuffer = Buffer.alloc(Server.default, Server.default.sampleRate*bufferLength);
		feedbackGroup = Group.new;
		effectGroup = Group.after(feedbackGroup);

		switch(
			Manticore.speakerConfiguration,
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

		effectSynths.size.do{|i|
			effectSynths[i] = Synth(
				"HomunculousFX",
				[
					"in",(HomunculusServerItems.audioToEffectsBus.index+i),
					"out",i

				],
				effectGroup
			);
		};
	}

	*free
	{
		feedbackGroup.free;
		effectGroup.free;
		feedbackBuffer.free;
		audioToEffectsBus.free;
	}
}

HomunculusColorScheme
{
	*synthStrokeColor
	{
		^Color.new(0.9,0.1,0.13);
	}

	*synthFillColor
	{
		^Color.new(0.7,0.05,0.1);
	}
}

HomunculusDesktop
{
	classvar mousePos;	// hold the mouse position in the interface
	classvar pMousePos;	// holds the previous mouse position
	var selectionArea = nil;
	var cursors; // holds all cursor syncObjects
	classvar <desktopObjects;
	classvar <bufferObjects;
	var selectedObjects;
	var osc;

	var wh = 500;

	*new
	{
		^super.new.init;
	}

	init
	{
		osc = List.new;
		cursors = Dictionary.new;
		desktopObjects = Dictionary.new;
		bufferObjects = List.new;
		selectedObjects = Set.new;
		mousePos = 0@0;
		pMousePos = mousePos;

		HomunculusServerItems.init;

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

	setBuffers
	{

		desktopObjects.keysDo{|objName|

			if(bufferObjects.indexOf(objName).isNil,{
				this.shouldSetBuffer(objName);
			});

		};

	}

	shouldSetBuffer
	{|objName|

		bufferObjects.do{|buf|
			if( desktopObjects.at(objName).origin.dist(desktopObjects.at(buf).origin) < desktopObjects.at(buf).zoneRad ,{
				desktopObjects.at(objName).synth.set("bufnum",desktopObjects.at(buf).feedbackBuffer);
				desktopObjects.at(objName).activeBufName = buf;
			});
		};

	}

	setupOSClisteners
	{
		// addSyncObject
		osc.add(OSCthulhu.onAddSyncObject(\addObjects, {|msg|
			var oscAddr, objName, group, subGroup, args;

			# oscAddr, objName, group, subGroup = msg;
			args = msg.copyRange(4,msg.size-1);

			// cursors
			if(subGroup.asString == "curs",{
				cursors.put(objName,HomunculusCursor(args[0], Point(args[1],args[2])));
			});

			// desktop objects
			if(subGroup.asString.contains("s_"),{
				desktopObjects.put(objName,
					HomunculusDesktopObject.new(
						subGroup.asSymbol,
						Point(args[0],args[1])
					)
				);
			});

			if(subGroup.asString.contains("b_"),{
				bufferObjects.add(objName);
				desktopObjects.put(objName,
					HomunculusDesktopBufferObject.new(
						subGroup.asSymbol,
						Point(args[0],args[1])
					)
				);
			});

			this.setBuffers;

			});
		);

		// setSyncArg
		osc.add(OSCthulhu.onSetSyncArg(\updateObjects, {|msg|
			var oscAddr, objName, argIndex, argValue, group, subGroup;

			# oscAddr, objName, argIndex, argValue, group, subGroup = msg;

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
				subGroup.asString.contains("s_"), {
					switch(
						argIndex,
						0, {
							desktopObjects.at(objName).setX(argValue);
							desktopObjects.at(objName).synth.set("argA",argValue.linlin(0,wh,0.0,1.0));
						},
						1, {
							desktopObjects.at(objName).setY(argValue);
							desktopObjects.at(objName).synth.set("argB",argValue.linlin(0,wh,0.0,1.0));
						},
						2, {
							desktopObjects.at(objName).selectedBy = argValue.asString;

							if(argValue.asString == OSCthulhu.userName.asString,{
								selectedObjects.add(objName);
								},{
									selectedObjects.remove(objName);
							});

						}
					);

					this.setBuffers;

			});

			if(
				subGroup.asString.contains("b_"), {
					switch(
						argIndex,
						0, {
							desktopObjects.at(objName).setX(argValue);
						},
						1, {
							desktopObjects.at(objName).setY(argValue);
						},
						2, {
							desktopObjects.at(objName).selectedBy = argValue.asString;

							if(argValue.asString == OSCthulhu.userName.asString,{
								selectedObjects.add(objName);
								},{
									selectedObjects.remove(objName);
							});

						}
					);

					this.setBuffers;

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

			if(subGroup.asString.contains("s_"), {
				desktopObjects.at(objName).free;
				desktopObjects.removeAt(objName);
			});


			if(subGroup.asString.contains("b_"), {
				desktopObjects.at(objName).free;
				desktopObjects.removeAt(objName);
				bufferObjects.remove(objName);

				desktopObjects.keysDo{|obj|
					if( desktopObjects.at(obj).activeBufName == objName ,{
						desktopObjects.at(obj).activeBufName = nil;
					});
				};
			});

			selectedObjects.remove(objName);

			this.setBuffers;

			})
		);

	}

	getView
	{
		var scrollView, interface;

		scrollView = [ScrollView(),stretch:6];

		interface = UserView(scrollView[0], Rect(0,0,wh,wh));
		interface.background_(Color.gray(0.1));
		interface.clearOnRefresh = true;
		interface.animate = true;

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

			OSCthulhu.addSyncObject(
				type ++ rrand(1000,9999), Manticore.group, type, [x,y,"none"]
			);

		});

		// drawing
		interface.drawFunc = {

			// objects
			desktopObjects.do{|obj|

				if(obj.activeBufName.notNil,{
					Pen.strokeColor = Color.red;
					Pen.line( obj.origin, desktopObjects.at(obj.activeBufName).origin );
				});

				obj.draw;
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

	free
	{
		osc.collect({|def| def.free; });
		cursors.collect({|i| i.free; });
		OSCthulhu.removeSyncObject(OSCthulhu.userName.asString++"Curs");

		HomunculusServerItems.free;
	}
}

HomunculusObjectWindow
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

							{objListView.removeAll}.defer;

							{objListView.canvas = this.pupulateObjectList(); }.defer;

							currentScoreIndex = argValue;
						});
					},
					1, {
						{volSlider.value_( argValue )}.defer;
						HomunculusServerItems.feedbackGroup.set("amp",argValue.asFloat);
						HomunculusServerItems.effectGroup.set("amp",argValue.asFloat);
						HomunculusServerItems.currentVolume = argValue;
					}
				)
			});
			})
		);
	}

	getView
	{
		var container, objListLayout, globalControls;

		container = View();

		objListView = ScrollView();
		objListView.canvas = this.pupulateObjectList();

		volSlider = Slider().orientation_(\horizontal);
		volSlider.action_({|s|
			OSCthulhu.setSyncArg("HM_Global", 1, s.value.asFloat);
		});

		globalControls = View();
		globalControls.layout_(
			VLayout(
				StaticText().string_("Volume"),
				volSlider,
				DragSource()
				.background_(Color.black)
				.object_(\b_Buffer)
				.string_("Buffer")
				.dragLabel_("Buffer")
			)
		);

		container.layout_(
			VLayout(
				StaticText().string_("Objects"),
				[objListView, stretch:2],
				StaticText().string_("Global Controls"),
				globalControls,
				[Button().states_([["Deselect All"]]).action_({ HomunculusDesktop.deselectAll; }),stretch:1]
			)
		);

		^[container,stretch:1];
	}

	pupulateObjectList
	{
		var objList;

		objList = View();
		objList.layout_(VLayout());

		Homunculus.scoreObjects[Homunculus.scoreIndex].do{|item|
			objList.layout.add(
				DragSource()
				.background_(HomunculusColorScheme.synthFillColor(item.split($_)[0]))
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

HomunculusSelectionArea
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

/*
* Represents/displays remote user's mouse cursors on the desktop
*/
HomunculusCursor
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
HomunculusDesktopObject
{
	var <>origin;		// center point
	var <handleRad = 20;	// radius of the clickable area
	var <zoneRad = 20;	// radius of the influence area
	var <>selectedBy = "none";	// user who has this selected
	var <>clickOffset;		// used to handle dragging
	var <>activeBufName;

	var <type;
	var <synth = nil;	// synth represented by the object

	*new
	{|t,pos|
		^super.new.init(t,pos);
	}

	init
	{|t,pos|

		activeBufName = nil;
		origin = pos;
		type = t.asString.split($_)[1];
		synth = Synth.tail(
			HomunculusServerItems.feedbackGroup,
			type,
			[
				"bufnum",HomunculusServerItems.feedbackBuffer,
				"amp",HomunculusServerItems.currentVolume,
				"argA",pos.x.linlin(0,500,0.0,1.0),
				"argB",pos.y.linlin(0,500,0.0,1.0),
				"out",HomunculusServerItems.audioToEffectsBus
			]
		);
	}

	free
	{
		synth.release;
	}

	setX
	{|xPos|
		origin.x = xPos;
	}

	setY
	{|yPos|
		origin.y = yPos;
	}

	draw
	{
		Pen.addArc(origin,handleRad,0,2pi);
		Pen.fillColor = HomunculusColorScheme.synthFillColor;
		Pen.strokeColor = HomunculusColorScheme.synthStrokeColor;
		Pen.draw(3);
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
* Superclass for all desktop object types
*/
HomunculusDesktopBufferObject
{
	var <>origin;		// center point
	var <handleRad = 20;	// radius of the clickable area
	var <zoneRad = 100;	// radius of the influence area
	var <>selectedBy = "none";	// user who has this selected
	var <>clickOffset;		// used to handle dragging
	var <feedbackBuffer;
	var <>activeBufName;

	var <type;

	*new
	{|t,pos|
		^super.new.init(t,pos);
	}

	init
	{|t,pos|
		origin = pos;
		activeBufName = nil;
		type = t.asString.split($_)[1];

		feedbackBuffer = Buffer.alloc(Server.default, Server.default.sampleRate*HomunculusServerItems.bufferLength);
	}

	free
	{
		feedbackBuffer.free;
	}

	setX
	{|xPos|
		origin.x = xPos;
	}

	setY
	{|yPos|
		origin.y = yPos;
	}

	draw
	{
		Pen.addArc(origin,handleRad,0,2pi);
		Pen.fillColor = HomunculusColorScheme.synthFillColor;
		Pen.strokeColor = HomunculusColorScheme.synthStrokeColor;
		Pen.draw(3);

		Pen.addArc(origin,zoneRad,0,2pi);
		Pen.draw(2);
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