FROM arm32v7/python:3.7-buster 
#ADD led-demo.py /
COPY . .
RUN pip3 install --no-cache-dir gpiozero  requests RPi.GPIO elastic-apm[flask]
CMD [ "python3", "./app.py" ]
