import subprocess
import os
import random

binary_prefix = "java -cp %s/build/libs/CacheBenchmark-1.0-SNAPSHOT-all.jar" % os.getcwd()

port = random.randint(1000, 60000)


class RemoteProcess:
    def __init__(self, host, command):
        self.host = host
        self.command = command
        self.remote = None

    def start(self):
        self.remote = subprocess.Popen(["ssh", self.host, self.command])
        print("start binary on %s with %s" % (self.host, self.command))

    def kill(self):
        self.remote.kill()
        print("stop host %s" % self.host)

    def wait(self):
        self.remote.wait()


class Server(RemoteProcess):
    def __init__(self, host):
        command = "%s nebula.io.CacheServer %s %d" % (binary_prefix, host, port)
        RemoteProcess.__init__(self, host, command)


class KillServer(RemoteProcess):
    def __init__(self, host):
        command = "killall %s" % \
                  server_binary
        RemoteProcess.__init__(self, host, command)


class Client(RemoteProcess):
    def __init__(self, host, server_host):
        command = "%s nebula.io.CacheClient %s %s %d" % (binary_prefix, host, server_host, port)
        RemoteProcess.__init__(self, host, command)
