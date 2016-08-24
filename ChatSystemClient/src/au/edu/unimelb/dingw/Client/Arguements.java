package au.edu.unimelb.dingw.Client;

/**
 * Created by dingwang on 15/9/18.
 */
import org.kohsuke.args4j.Argument;
import org.kohsuke.args4j.Option;

public class Arguements {

    @Argument
    public String remoteServer;
    @Option(name="-p", usage = "Port")
    public int port = 4444;
}
