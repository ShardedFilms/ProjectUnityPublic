package unity.content.blocks;

import arc.*;
import arc.graphics.*;
import arc.graphics.g2d.*;
import mindustry.content.*;
import mindustry.type.*;
import mindustry.world.*;
import mindustry.world.blocks.environment.*;
import mindustry.world.meta.*;
import unity.annotations.Annotations.*;
import unity.content.*;
import unity.graphics.*;
import unity.world.blocks.*;
import unity.world.blocks.distribution.*;
import unity.world.blocks.distribution.SimpleTransmission.*;
import unity.world.blocks.envrionment.*;
import unity.world.blocks.payloads.*;
import unity.world.blocks.power.*;
import unity.world.blocks.production.*;
import unity.world.blocks.units.*;
import unity.world.graph.*;

import static mindustry.type.ItemStack.with;

//frankly i do not wish to have my ide lag from an enormous unnavigable UnityBlocks file
public class YoungchaBlocks{
    ///environmental
    public static @FactionDef("youngcha")
    Block
    oreNickel, concreteBlank, concreteFill, concreteNumber, concreteStripe, concrete, stoneFullTiles, stoneFull,
    stoneHalf, stoneTiles, pit,waterpit, greySand, nickelGeode,greysandWall;
    //non environmental
    public static @FactionDef("youngcha")
    Block
    //torque
        //transmission
        driveShaft, shaftRouter, smallTransmission, torqueMeter,driveBeltSmall,driveBeltLarge,
        //power
        windTurbine, rotaryWaterExtractor, flywheel, torqueSource,
        //production
        augerDrill, wallDrill,
        //payload
        inserterArm,
    ///heat
        //transmission
        heatPipe, steamPiston,
        //power
        combustionHeater,thermalHeater,seebeckGenerator,smallRadiator,
    //crucible
        //crafting
        crucible,crucibleChannel,cruciblePump,crucibleCaster,payloadCaster,crucibleSource,
    //modules
        basicPanel,
    //other
    sandboxAssembler, monomialHangar; // monomial, binomial then polynomial (maybe meromorphic for the t6-t7 equiv massive unit)

    public static void load(){
        oreNickel = new UnityOreBlock(UnityItems.nickel){{
            oreScale = 24.77f;
            oreThreshold = 0.913f;
            oreDefault = false;
        }};
        greySand = new Floor("grey-sand"){{
            variants = 3;
            itemDrop = Items.sand;
            playerUnmineable = true;
        }};
        concreteBlank = new Floor("concrete-blank"){{
            attributes.set(Attribute.water, -0.85f);
        }};
        concreteFill = new Floor("concrete-fill"){{
            variants = 0;
            attributes.set(Attribute.water, -0.85f);
        }};
        concreteNumber = new Floor("concrete-number"){{
            variants = 10;
            attributes.set(Attribute.water, -0.85f);
        }};
        concreteStripe = new Floor("concrete-stripe"){{
            attributes.set(Attribute.water, -0.85f);
        }};
        concrete = new Floor("concrete"){{
            attributes.set(Attribute.water, -0.85f);
        }};
        stoneFullTiles = new Floor("stone-full-tiles"){{
            attributes.set(Attribute.water, -0.75f);
        }};
        stoneFull = new Floor("stone-full"){{
            attributes.set(Attribute.water, -0.75f);
        }};
        stoneHalf = new Floor("stone-half"){{
            attributes.set(Attribute.water, -0.5f);
        }};
        stoneTiles = new Floor("stone-tiles"){{
            attributes.set(Attribute.water, -0.5f);
        }};
        pit = new Floor("pit"){{
                buildVisibility = BuildVisibility.editorOnly;
                cacheLayer = UnityShaders.pitLayer;
                placeableOn = false;
                solid = true;
                variants = 0;
                canShadow = false;
                mapColor = Color.black;
            }
            @Override
            public TextureRegion[] icons(){
                return new TextureRegion[]{Core.atlas.find(name + "-icon", name)};
            }
        };
        waterpit= new Floor("waterpit"){{
                buildVisibility = BuildVisibility.editorOnly;
                cacheLayer = UnityShaders.waterpitLayer;
                placeableOn = true;
                isLiquid = true;
                drownTime = 20f;
                speedMultiplier = 0.1f;
                liquidMultiplier = 2f;
                status = StatusEffects.wet;
                statusDuration = 120f;
                variants = 0;
                liquidDrop = Liquids.water;
                canShadow = false;
                mapColor = Liquids.water.color.cpy().lerp(Color.black,0.5f);
            }
            @Override
            public TextureRegion[] icons(){
                return new TextureRegion[]{Core.atlas.find(name + "-icon", name)};
            }
        };
        nickelGeode = new LargeStaticWall("nickel-geode"){{
            variants = 2;
            itemDrop = UnityItems.nickel;
            maxsize = 3;
        }};
        greysandWall = new LargeStaticWall("grey-sand-wall"){{
           variants = 3;
           itemDrop = Items.sand;
           maxsize = 3;
        }};

        ///////

        driveShaft = new DriveShaft("drive-shaft"){{
            health = 300;

            config.nodeConfig.put(TorqueGraph.class, b -> new TorqueGraphNode(0.005f, 3f, b));
            config.fixedConnection(TorqueGraph.class, 1, 0, 1, 0);
            requirements(Category.power, with(Items.copper, 10, Items.lead, 10));
        }};
        shaftRouter = new GenericGraphBlock("shaft-router"){{
            requirements(Category.power, with(Items.copper, 20, Items.lead, 20));
            health = 350;
            solid = true;

            config.nodeConfig.put(TorqueGraph.class, b -> new TorqueGraphNode(0.04f, 4f, b));
            config.fixedConnection(TorqueGraph.class, 1, 1, 1, 1);
        }};
        smallTransmission = new SimpleTransmission("small-transmission"){{
            requirements(Category.power, with(UnityItems.nickel, 20, Items.copper, 20, Items.lead, 20));
            health = 1100;
            size = 2;
            config.nodeConfig.put(TorqueGraph.class, b -> new TransmissionTorqueGraphNode(0.05f, 8f, 2,b));
            config.fixedConnection(TorqueGraph.class, 0, 1, 0, 0, 1, 0, 0, 0);
            config.fixedConnection(TorqueGraph.class, 1, 0, 0, 0, 0, 1, 0, 0);
        }};
        torqueMeter = new TorqueMeter("torque-meter"){{
            requirements(Category.power, with(UnityItems.nickel, 20, Items.lead, 30));
            health = 250;
            rotate = true;
            solid = true;

            config.nodeConfig.put(TorqueGraph.class, b -> new TorqueMeterGraphNode(0.01f, 5f, b));
            config.fixedConnection(TorqueGraph.class, 1, 0, 1, 0);

        }};
        driveBeltSmall = new DriveBelt("small-drive-belt"){{
            requirements(Category.power, with(UnityItems.nickel, 50, Items.graphite, 20));
            health = 150;
            rotate = true;
            solid = true;
            config.nodeConfig.put(TorqueGraph.class, b -> new TransmissionTorqueGraphNode(0.03f, 8f, 1,b));
            config.fixedConnection(TorqueGraph.class, 0, 0, 1, 0);
            config.distanceConnection(TorqueGraph.class, 1);
        }};
        driveBeltLarge = new DriveBelt("large-drive-belt"){{
            requirements(Category.power, with(UnityItems.cupronickel, 30, Items.silicon, 40, Items.graphite, 50));
            health = 1750;
            size = 3;
            maxRange = 10;
            wheelSize = 8;
            rotate = true;
            solid = true;
            config.nodeConfig.put(TorqueGraph.class, b -> new TransmissionTorqueGraphNode(0.05f, 30f, 1,b));
            config.fixedConnection(TorqueGraph.class, 0, 0, 0,  0, 0, 0,  0, 1, 0,  0, 0, 0);
            config.distanceConnection(TorqueGraph.class, 6);
        }};
        torqueSource = new TorqueSource("torque-source"){{
            solid = true;
            requirements(Category.power,BuildVisibility.sandboxOnly,with());
            config.nodeConfig.put(TorqueGraph.class, b -> new TorqueGraphNode(0.001f, 999999f, b));
                        config.fixedConnection(TorqueGraph.class, 1, 1, 1, 1);
        }};
        windTurbine = new WindTurbine("wind-turbine"){{
            requirements(Category.power, with(Items.titanium, 20, Items.lead, 80, Items.copper, 70));
            health = 2600;
            size = 3;

            config.nodeConfig.put(TorqueGraph.class, b -> new WindTurbineTorqueGraphNode(0.03f, 20f, 1.5f, 20f, b));
            config.fixedConnection(TorqueGraph.class, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0);
        }};
        rotaryWaterExtractor = new RotaryWaterExtractor("rotary-water-extractor"){{
            health = 2600;
            size = 3;
            result = Liquids.water;
            pumpAmount = 0.5f;
            liquidCapacity = 60f;
            rotateSpeed = 1.4f;
            attribute = Attribute.water;

            config.nodeConfig.put(TorqueGraph.class, b -> new TorqueGraphNode(0.15f, 30f, 40,b));
            config.fixedConnection(TorqueGraph.class, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 1, 0);
            requirements(Category.production, with(Items.titanium, 50, UnityItems.nickel, 80, Items.metaglass, 30));
        }};

        augerDrill = new TorqueDrill("auger-drill"){{
            health = 2600;
            size = 3;
            tier = 3;
            drillTime = 400;
            requirements(Category.production, with(Items.lead, 60, Items.copper, 150));
            consumes.liquid(Liquids.water, 0.08f).boost();

            config.nodeConfig.put(TorqueGraph.class, b -> new TorqueGraphNode(0.1f, 50f, 40,b));
            config.fixedConnection(TorqueGraph.class, 0, 1, 0,  0, 0, 0,  0, 1, 0,  0, 0, 0);
        }};

        wallDrill = new SmallWallDrill("wall-drill"){{
            requirements(Category.production, with(Items.graphite, 40, UnityItems.nickel, 40,Items.titanium, 20));
            health = 1100;
            size = 2;
            tier = 3;
            range = 2;
            drillTime = 40;
            config.nodeConfig.put(TorqueGraph.class, b -> new TorqueGraphNode(0.07f, 20f, 50,b));
            config.fixedConnection(TorqueGraph.class, 0, 0,  0, 0,  1, 1,  0, 0);
        }};

        heatPipe = new HeatPipe("heat-pipe"){{
            requirements(Category.power, with(UnityItems.nickel, 5, Items.copper, 10));
            health = 200;
            solid = false;
            targetable = false;
            config.nodeConfig.put(HeatGraph.class, b -> new HeatGraphNode(b, 0.005f, 0.4f, 1,2500 + HeatGraphNode.celsiusZero));
            config.fixedConnection(HeatGraph.class, 1, 1, 1, 1);
        }};

        flywheel = new FlyWheel("flywheel"){{
            requirements(Category.power, with(UnityItems.nickel, 50, Items.titanium, 50, Items.lead, 150));
            size = 3;
            rotate = true;
            health = 2600;
            solid = true;
            config.nodeConfig.put(TorqueGraph.class, b -> new TorqueGraphNode(0.05f, 1000f, 30f,10,b));
            config.fixedConnection(TorqueGraph.class, 0, 1, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0);
        }};

        steamPiston = new SteamPiston("steam-piston"){{
            requirements(Category.power, with(Items.graphite, 20, UnityItems.nickel, 30, Items.titanium, 50, Items.lead, 150));
            size = 3;
            rotate = true;
            health = 2000;
            solid = true;
            consumes.liquid(Liquids.water, 0.1f);
            config.nodeConfig.put(HeatGraph.class, b -> new HeatGraphNode(b, 0.01f, 0.1f, 9, 1200 + HeatGraphNode.celsiusZero));
            config.fixedConnection(HeatGraph.class, 0,0,0, 0,0,0 ,0,1,0 ,0,0,0);
        }};

        inserterArm = new PayloadArm("inserter-arm"){{
            health = 150; // more delicate uwu

            config.nodeConfig.put(TorqueGraph.class, b -> new TorqueGraphNode(0.07f, 13f, 40,b));
            config.fixedConnection(TorqueGraph.class, 0, 1, 0, 1);
            requirements(Category.units, with(UnityItems.cupronickel, 20, Items.graphite, 15, Items.silicon, 20));
        }};

        combustionHeater = new CombustionHeater("combustion-heater"){{
            requirements(Category.power, with(UnityItems.nickel, 30, Items.lead, 70, Items.copper, 70));
            size = 2;
            rotate = true;
            health = 700;
            solid = true;
            config.nodeConfig.put(HeatGraph.class, b -> new HeatGraphNode(b, 0.01f, 0.1f, 4, 1500 + HeatGraphNode.celsiusZero, 1000 + HeatGraphNode.celsiusZero,0.015f));
            config.fixedConnection(HeatGraph.class, 1, 1,  0, 0,  0, 0,  0, 0);
        }};

        thermalHeater = new ThermalHeater("thermal-heater"){{
            size = 2;
            rotate = true;
            health = 1100;
            solid = true;
            floating = true;
            config.nodeConfig.put(HeatGraph.class, b -> new HeatGraphNode(b, 0.01f, 0.1f, 4, 1500 + HeatGraphNode.celsiusZero,1000 + HeatGraphNode.celsiusZero,0.015f));
            config.fixedConnection(HeatGraph.class, 1, 1,  0, 0,  0, 0,  0, 0);
            requirements(Category.power, with(UnityItems.nickel, 30, Items.graphite, 30, Items.copper, 100, UnityItems.cupronickel, 30));
        }};

        seebeckGenerator = new SeebeckGenerator("seebeck-generator"){{
            size = 3;
            rotate = true;
            health = 2200;
            solid = true;
            hasPower = true;
            config.nodeConfig.put(HeatGraph.class, b -> new HeatGraphNode(b, 0.01f, 0.01f,9, 1800 + HeatGraphNode.celsiusZero));
            config.fixedConnection(HeatGraph.class, 0,1,0,  0,0,0,  0,1,0  ,0,0,0);
            requirements(Category.power, with(UnityItems.nickel, 50, Items.graphite, 30, Items.copper, 120,Items.titanium, 100, UnityItems.cupronickel, 30));
        }};
        smallRadiator = new HeatRadiator("small-radiator"){{

            size = 2;
            rotate = true;
            health = 1100;
            solid = true;
            config.nodeConfig.put(HeatGraph.class, b -> new HeatGraphNode(b, 0.4f, 0.15f, 4,1800 + HeatGraphNode.celsiusZero));
            config.fixedConnection(HeatGraph.class, 0, 0,  1, 1,  0, 0,  1, 1);
            requirements(Category.power, with(UnityItems.nickel, 30, Items.graphite, 30, Items.copper, 100, UnityItems.cupronickel, 30));
        }};

        crucible = new CrucibleBlock("crucible"){{
            requirements(Category.crafting, with(UnityItems.nickel, 30, Items.graphite, 30, Items.titanium, 50));
            size = 3;
            health = 1700;
            solid = true;

            config.nodeConfig.put(HeatGraph.class, b -> new HeatGraphNode(b, 0.015f, 0.15f, 9,1800 + HeatGraphNode.celsiusZero));
            config.fixedConnection(HeatGraph.class, 1,0,1,  1,0,1,  1,0,1,  1,0,1);
            config.nodeConfig.put(CrucibleGraph.class, b -> new CrucibleGraphNode(b,50));
            config.fixedConnection(CrucibleGraph.class, 0,1,0,  0,1,0,  0,1,0,  0,1,0);
        }};

        crucibleChannel = new CrucibleChannel("crucible-channel"){{
            requirements(Category.crafting, with(UnityItems.nickel, 10, Items.graphite, 10));
            health = 300;
            config.nodeConfig.put(CrucibleGraph.class, b -> new CrucibleGraphNode(b,5));
            config.fixedConnection(CrucibleGraph.class, 1,1,1,1);
        }};

        cruciblePump = new CruciblePump("crucible-pump"){{
            requirements(Category.crafting, with(UnityItems.nickel, 30, Items.graphite, 30, Items.titanium, 30));
            health = 300;
            rotate = true;
            solid = true;

            config.nodeConfig.put(CrucibleGraph.class, b -> new CrucibleGraphNode(b,5));
            config.fixedConnection(CrucibleGraph.class, 0,0,1,0);
            config.fixedConnection(CrucibleGraph.class, 1,0,0,0);

            config.nodeConfig.put(TorqueGraph.class, b -> new TorqueGraphNode(0.1f, 10f, b));
            config.fixedConnection(TorqueGraph.class, 0, 1, 0, 1);
        }};

        crucibleCaster = new CrucibleCaster("casting-mold"){{
            requirements(Category.crafting, with(UnityItems.nickel, 60, Items.graphite, 50));
            health = 1700;
            rotate = true;
            solid = true;
            size = 3;
            itemCapacity = 4;
            hasItems = true;

            config.nodeConfig.put(CrucibleGraph.class, b -> new CrucibleGraphNode(b,5));
            config.fixedConnection(CrucibleGraph.class, 0,0,0, 0,0,0, 0,1,0, 0,0,0);

            config.nodeConfig.put(TorqueGraph.class, b -> new TorqueGraphNode(0.1f, 100f, 15,b));
            config.fixedConnection(TorqueGraph.class, 0,0,0,  0,1,0, 0,0,0, 0,1,0);
        }};

        payloadCaster= new PayloadCaster("payload-casting-mold"){{
            requirements(Category.crafting, with(UnityItems.cupronickel, 30, Items.metaglass, 30,Items.graphite, 50));
            health = 1700;
            rotate = true;
            solid = true;
            size = 3;
            moveTime = 50;

            config.nodeConfig.put(CrucibleGraph.class, b -> new CrucibleGraphNode(b,50));
            config.fixedConnection(CrucibleGraph.class, 0,0,0, 0,0,0, 0,1,0, 0,0,0);

            config.nodeConfig.put(TorqueGraph.class, b -> new TorqueGraphNode(0.1f, 100f,40, b));
            config.fixedConnection(TorqueGraph.class, 0,0,0,  0,1,0, 0,0,0, 0,1,0);
        }};

        crucibleSource = new CrucibleSource("crucible-source"){{
            solid = true;
            requirements(Category.crafting,BuildVisibility.sandboxOnly,with());
            config.nodeConfig.put(CrucibleGraph.class, b -> new CrucibleGraphNode(b,99));
            config.fixedConnection(CrucibleGraph.class, 1,1,1,1);
        }};

        sandboxAssembler = new ModularUnitAssembler("sandbox-assembler"){{
            requirements(Category.units, BuildVisibility.sandboxOnly, with());
            size = 3;
            health = 69420;
            sandbox = true;
        }};
        monomialHangar  = new ModularUnitAssembler("monomial-hangar"){{
            requirements(Category.units, BuildVisibility.hidden,  with(Items.copper,100,Items.graphite,20, Items.silicon,20));
            size = 3;
            health = 2600;
            unitModuleWidth = 3;
            unitModuleHeight = 4;
            rotate = true;
        }};


        //modules
        basicPanel = new ModuleBlock("module-basic-panel"){{
            requirements(Category.crafting, BuildVisibility.hidden,with(UnityItems.nickel, 8));
        }};
    }
}
