package au.edu.unimelb.dingw.Server.Tools;

/**
 * Created by dingwang on 15/9/18.
 */

import org.kohsuke.args4j.Option;


public class Arguements {
    @Option(name = "-p", usage = "Determine the port")
    public Integer port = 4444;
}
