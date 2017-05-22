#! /usr/bin/env python

"""
Client for playing Battleship game with service.
"""

import sys
import logging
import argparse
import json
import requests


BASE_URL = 'http://localhost:8080'

def get_parser():
    """
    Generate a new argument parser.
    """
    new_parser = argparse.ArgumentParser(description='Battleship client.')
    new_parser.add_argument('--play', action='store_true',
                            help='Take a turn.')
    new_parser.add_argument('-p', '--player', type=str,
                            help='Name of player.')
    new_parser.add_argument('-g', '--game-id', type=str,
                            help='The game ID.')
    new_parser.add_argument('-c', '--coord', type=str,
                            help='Coordinate to fire.')
    new_parser.add_argument('--reset', action='store_true',
                            help='Reset game state.')
    new_parser.add_argument('--start', action='store_true',
                            help='Start a game.')
    new_parser.add_argument('-s', '--status', action='store_true',
                            help='Return state of board.')

    return new_parser

def validate_args(val_args):
    """
    Validate correct arguments are present.
    """
    result = True

    if val_args.play:
        if not validate_string_arg(val_args.game_id):
            logging.error('Must specify a game ID.')
            result = False
        # coordinate specified, making a move
        c_split = val_args.coord.split(',') if val_args.coord else []
        if len(c_split) != 2 \
            or not c_split[0].isdigit() \
            or not c_split[1].isdigit():
            logging.error('Must specify two numeric coordinates (ex. 1,5).')
            result = False
    elif val_args.start:
        if not validate_string_arg(val_args.player):
            logging.error('Must specify player name.')
            result = False
    elif val_args.reset or val_args.status:
        if not validate_string_arg(val_args.game_id):
            logging.error('Must specify a game ID.')
            result = False
    else:
        logging.error('Must specify command: start, play, status, reset.')
        result = False

    return result

def validate_string_arg(value):
    """
    Validate a string argument is present."
    """
    if not value or len(value) < 1:
        return False

    return True

def build_request(build_args):
    """
    Build web request to Java service.
    """
    if build_args.play:
        return requests.post(BASE_URL + '/play/{}'.format(build_args.game_id),
                             headers={'Content-Type': 'application/json'},
                             data=json.dumps({'coord': [int(x) for x in build_args.coord.split(',')] }))
    elif build_args.status:
        return requests.get(BASE_URL + '/status/{}'.format(build_args.game_id),
                            headers={'Content-Type': 'application/json'},
                            params={})
    elif build_args.start:
        return requests.post(BASE_URL + '/start-game',
                             headers={'Content-Type': 'application/json'},
                             data=json.dumps({'player': build_args.player}))
    elif build_args.reset:
        return requests.delete(BASE_URL + '/reset-game/{}'.format(build_args.game_id))
    return None


def run_main():
    """
    Process arguments and send command.
    """
    parser = get_parser()

    print 'Parsing args.'
    args = parser.parse_args()
    print 'Args: ', args
    print 'Done parsing args.'

    if not validate_args(args):
        sys.exit(-1)

    request = build_request(args)

    if request:
        print "Service returned: ", request.status
        print request.json()
    else:
        print "Invalid command."


if __name__ == "__main__":
    run_main()
