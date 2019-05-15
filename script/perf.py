#!/usr/bin/env python
import time

from remote import Client
from remote import Server

hosts = []
# numbers = [4, 20, 2, 3, 5, 6, 14, 15, 16, 18, 19, 21]
# numbers = [12, 14, 14, 15, 15, 16, 16, 18, 18, 19, 19, 20, 20, 21, 21]
numbers = [14, 15, 15, 15, 15, 16, 16, 16, 18, 18, 19, 19, 20, 20, 21, 21]
# numbers = [4, 12, 12, 15, 15, 16, 16, 18, 18, 19, 19]
# numbers = [4, 4, 4, 4, 4, 4, 4, 4, 4]

print("sever number:%d" % (len(numbers) - 1))

for number in numbers:
    # hosts.append("202.45.128.%d" % (number + 159))
    hosts.append("10.22.1.%d" % number)

server_host = hosts[0]
client_hosts = hosts[1:]

if __name__ == '__main__':
    server = Server(server_host)
    server.start()

    time.sleep(2)

    clients = []
    for client_host in client_hosts:
        client = Client(client_host, server_host)
        client.start()
        clients.append(client)

    for client in clients:
        client.wait()
