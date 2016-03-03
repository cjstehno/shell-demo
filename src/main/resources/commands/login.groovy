import com.example.ShellDemoApplication

welcome = { ->
    def hostName;
    try {
        hostName = java.net.InetAddress.getLocalHost().getHostName();
    } catch (java.net.UnknownHostException ignore) {
        hostName = 'localhost';
    }

    String banner = ShellDemoApplication.getResourceAsStream('/banner.txt').text

    return """
${banner}
Logged into $hostName @ ${new Date()}
"""
}

prompt = { ->
    return "% ";
}
