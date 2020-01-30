"""
    Start a http server (Tornado) with a single thread
    Please don't block the single thread event-loop.
    
    Created by: Edward Cacho Casas
"""
import config
#import pods
import constants
import json
#import tornado.web
#from tornado.gen import multi
#from tornado.httpclient import HTTPClientError
#from tornado.httpserver import HTTPServer
#from tornado.ioloop import IOLoop
from flask import Flask
from gpiozero import LED,CPUTemperature
from time import sleep
from elasticapm.contrib.flask import ElasticAPM
from elasticapm import Client
from elasticapm.contrib.opentracing import Tracer


import requests
import logging
import os

app = Flask(__name__)
tracer = Tracer()

dapr_port = os.getenv("DAPR_HTTP_PORT", 3500)
dapr_url = "http://localhost:{}/v1.0/bindings/measure-dapr".format(dapr_port)

client = Client({'SERVICE_NAME': 'pi-demo'})

@elasticapm.begin_transaction("cputemp")
async def dapr(temp: float):
    payload = {"data": {"device": "pi",
                        "signalType": "Temp",
                        "magnitude": "Cº",
                        "value": temp}}
    return requests.post(dapr_url, json=payload)

def main():

    red = LED(26)

    apm = ElasticAPM(app)

    #dapr_port = os.getenv("DAPR_HTTP_PORT", 3500)
    #dapr_url = "http://localhost:{}/v1.0/bindings/measure-dapr".format(dapr_port)
    cpu = CPUTemperature(min_temp=50, max_temp=90)
    logging.info('Initial temperature: {}C'.format(cpu.temperature))

    while True:
        #payload = {"data": {"device": "pi",
        #                    "signalType": "Temp",
        #                    "magnitude": "Cº",
        #                    "value": cpu.temperature}}
        try:
            #client.begin_transaction("cputemp")
            response = dapr(cpu.temperature)
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