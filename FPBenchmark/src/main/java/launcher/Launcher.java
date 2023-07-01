package launcher;

import dedis.fp.FPBenchmark;
import org.apache.commons.cli.*;

import java.io.IOException;

public class Launcher {

    public static void launchBenchmark(String[] args) throws ParseException {
        String libName = null;
        String[] funcNames = null;
        CommandLine cmd;

        String dirpath = "";
        int seed = -1;
        int wc = 0;
        int ec = 0;

        Options options = new Options();
        Option optLibname = Option.builder("l").hasArg().required(true).desc("library name").build();
        Option optFname = Option.builder("f").hasArg().required(true).desc("function name(s)").build();
        Option optSeed = Option.builder("s").hasArg().required(true).desc("seed").build();
        Option optWc = Option.builder("w").hasArg().required(true).desc("warmup count").build();
        Option optEc = Option.builder("e").hasArg().required(true).desc("execution count").build();
        Option optDirpath = Option.builder("d").hasArg().required(true).desc("output directory path").build();

        optFname.setArgs(Option.UNLIMITED_VALUES);
        options.addOption(optLibname);
        options.addOption(optFname);
        options.addOption(optSeed);
        options.addOption(optWc);
        options.addOption(optEc);
        options.addOption(optDirpath);

        CommandLineParser parser = new DefaultParser();
        cmd = parser.parse(options, args);
        if (cmd.hasOption("l")) {
            libName = cmd.getOptionValue("l");
        } else {
            System.err.println("Missing library name");
            System.exit(1);
        }
        if (cmd.hasOption("f")) {
            funcNames = cmd.getOptionValues("f");
        } else {
            System.err.println("Missing function name(s)");
            System.exit(1);
        }
        if (cmd.hasOption("s")) {
            seed = Integer.parseInt(cmd.getOptionValue("s", "19"));
        } else {
            System.err.println("Missing seed");
            System.exit(1);
        }
        if (cmd.hasOption("w")) {
            wc = Integer.parseInt(cmd.getOptionValue("w", "20000"));
        } else {
            System.err.println("Missing warmup count");
            System.exit(1);
        }
        if (cmd.hasOption("e")) {
            ec = Integer.parseInt(cmd.getOptionValue("e", "100"));
        } else {
            System.err.println("Missing execution count");
            System.exit(1);
        }
        if (cmd.hasOption("d")) {
            dirpath = cmd.getOptionValue("d");
        } else {
            System.err.println("Missing output directory path");
            System.exit(1);
        }

        FPBenchmark fpBenchmark = new FPBenchmark(libName, funcNames, dirpath, seed, wc, ec);
        try {
            fpBenchmark.runBenchmarks();
        } catch (IOException e) {
            e.printStackTrace();
            System.exit(1);
        }
    }

    public static void main(String[] args) throws ParseException {
        launchBenchmark(args);
    }
}
