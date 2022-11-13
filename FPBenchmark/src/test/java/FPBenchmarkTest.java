import dedis.fp.FPBenchmark;
import org.apache.commons.cli.*;

import java.io.IOException;

public class FPBenchmarkTest {

    public static void main(String[] args) throws ParseException {

        String libName = null;
        String[] funcNames = null;
        CommandLine cmd;

        String icStr = System.getenv("INPUT_COUNT");
        String wcStr = System.getenv("WARMUP_COUNT");
        String ecStr = System.getenv("EXEC_COUNT");
        String dirpath = System.getenv("DIRPATH");
        if (icStr == null || wcStr == null || ecStr == null || dirpath == null) {
            System.err.println("Environment variables are not set");
            System.exit(1);
        }

        int seed = -1;
        int ic = Integer.parseInt(icStr);
        int wc = Integer.parseInt(wcStr);
        int ec = Integer.parseInt(ecStr);

        Options options = new Options();
        Option optLibname = Option.builder("l").hasArg().required(true).desc("library name").build();
        Option optFname = Option.builder("f").hasArg().required(true).desc("function name(s)").build();
        Option optSeed = Option.builder("s").hasArg().required(true).desc("seed").build();
        optFname.setArgs(Option.UNLIMITED_VALUES);
        options.addOption(optLibname);
        options.addOption(optFname);
        options.addOption(optSeed);

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

        FPBenchmark experiment = new FPBenchmark(libName, funcNames, dirpath, seed, ic, wc, ec);
        try {
            experiment.runBenchmarks();
        } catch (IOException e) {
            e.printStackTrace();
            System.exit(1);
        }
    }
}
