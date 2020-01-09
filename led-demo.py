from gpiozero import LED,CPUTemperature
from time import sleep
import requests
import logging
import os

logging.basicConfig(level=logging.DEBUG)
logging.debug('This will get logged')
print('Saludos Terricolas')
red = LED(26)
dapr_port = os.getenv("DAPR_HTTP_PORT", 3500)
dapr_url = "http://localhost:{}/v1.0/bindings/measure-dapr".format(dapr_port)
cpu = CPUTemperature(min_temp=50, max_temp=90)
logging.info('Initial temperature: {}C'.format(cpu.temperature))
logging.debug('This will get logged 2')
while True:
    logging.debug('This will get logged 3')
    payload = {"device": "pi",
               "signalType": "Temp",
               "magnitude": "Cº",
               "value": cpu.temperature}
    try:
        response = requests.post(dapr_url, json=payload)
        print(response.text, flush=True)
        red.on()
        sleep(1)
    except Exception as e:
        print(e)
        logging.error(e)
    red.off()
    sleep(10)
