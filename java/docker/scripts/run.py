#!/usr/bin/python
# coding=utf-8

import argparse

from functions import read_env_file
from functions import subtitute
from functions import print_some
from functions import print_args

parser = argparse.ArgumentParser(description='substitution for docker-compose.yml')

def add_arg(argname, comment): parser.add_argument(argname, type=str, help=comment)

add_arg('envFilePath',                   'env file with docker-compose variables')
add_arg('dockerComposeTemplateFilePath', 'docker-compose template file to fill with env variables')
add_arg('dockerComposeFilePath',         'docker-compose file to fill with template substitution result')

argsObject = parser.parse_args()
templateFilePath = argsObject.dockerComposeTemplateFilePath
print_args(argsObject)

enbFilePath = argsObject.envFilePath
envVariables = read_env_file(enbFilePath)

template = open(templateFilePath, 'r').read()
template = subtitute(template, envVariables)

open(argsObject.dockerComposeFilePath, 'w+').write(template)
print_some(templateFilePath, template)
