def get_from(path, key):
    envFile = open(path, 'r')
    envVariables = {}
    with envFile as fp:
        Lines = fp.readlines()
        for line in Lines:
            split = line.split("=")
            if len(split) > 1:
                envVariables[split[0]] = split[1].replace("\n", "")
    return envVariables[key]

def read_env_file(path):
    print(path)
    envFile = open(path, 'r')
    envVariables = {}
    with envFile as fp:
        Lines = fp.readlines()
        for line in Lines:
            if (line[0] == "#"): continue;
            split = line.split("=")
            if len(split) > 1:
                envVariables[split[0]] = split[1].replace("\n", "")
    return envVariables


def subtitute(template, env_variables):
    for x, y in env_variables.items():
        to_replace = "${" + x + "}"
        template = template.replace(to_replace, y)
    return template

def indent(max_length, string):
    return " " * (max_length - len(string))


def print_some(file_path, file_content):
    line = "\n" + "-" * len(file_path) + "\n"
    print(line + file_path + line)
    print(file_content)


def print_args(args_object):
    names = list(vars(args_object).keys())
    args = list(vars(args_object).values())
    max_length = max(map(len, names))
    for idx, val in enumerate(names):
        print("{}:{} ".format(val, indent(max_length, val)) + args[idx])
