#!/usr/bin/python
# coding=utf-8

import argparse

parser = argparse.ArgumentParser(description='substitution for docker-compose.yml')


def add_arg(argname, comment): parser.add_argument(argname, type=str, help=comment)


add_arg('key', 'key by which to get value from env file')
add_arg('env_file', '*.env file where key-value pais are contented')

argsObject = parser.parse_args()
key = argsObject.key
envFilePath = argsObject.env_file

envFile = open(envFilePath, 'r')
envVariables = {}
value = ""
with envFile as fp:
    Lines = fp.readlines()
    for line in Lines:
        split = line.split("=")
        if len(split) > 1 and split[0] == key:
            value = split[1].replace("\n", "")
            break
print(value)
