from gpiozero import LED,CPUTemperature
from time import sleep
import requests
import os

red = LED(26)

dapr_url = os.getenv("DAPR_HTTP_PORT","http://10.43.29.189:80/v1.0/bindings/measure-dapr")

while True:
    payload = {"device": "pi",
               "signalType": "Temp",
               "magnitude": "Cº",
               "value": CPUTemperature.value}
    try:
        response = requests.post(dapr_url, json=payload)
        print(response.text, flush=True)
        red.on()
        sleep(1)
    except Exception as e:
        print(e)
    red.off()
    sleep(10)
