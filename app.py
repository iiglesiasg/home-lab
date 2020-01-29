"""
    Start a http server (Tornado) with a single thread
    Please don't block the single thread event-loop.
    
    Created by: Edward Cacho Casas
"""
import config
#import pods
import constants
import json
import tornado.web
from tornado.gen import multi
from tornado.httpclient import HTTPClientError
from tornado.httpserver import HTTPServer
from tornado.ioloop import IOLoop
from gpiozero import LED,CPUTemperature
from time import sleep
from elasticapm.contrib.flask import ElasticAPM
from elasticapm import Client

import requests
import logging
import os


def main():

    red = LED(26)
    apm = ElasticAPM("home-lab", service_name='led-demo', secret_token='z9lp5srpkxs2jn5gknzvr8ml', logging=True)
    client = Client({'SERVICE_NAME': 'example'}, **defaults)
    dapr_port = os.getenv("DAPR_HTTP_PORT", 3500)
    dapr_url = "http://localhost:{}/v1.0/bindings/measure-dapr".format(dapr_port)
    cpu = CPUTemperature(min_temp=50, max_temp=90)
    logging.info('Initial temperature: {}C'.format(cpu.temperature))

    while True:
        payload = {"data": {"device": "pi",
                            "signalType": "Temp",
                            "magnitude": "Cº",
                            "value": cpu.temperature}}
        try:
            client.begin_transaction("cputemp")
            response = requests.post(dapr_url, json=payload)
            print(response.text, flush=True)
            red.on()
            sleep(1)
        except Exception as e:
            print(e)
            logging.error(e)
        red.off()
        sleep(10)

if __name__ == "__main__":
    main()