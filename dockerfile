FROM arm32v7/python:3.7-buster 
ADD led-demo.py /
RUN pip3 install --no-cache-dir gpiozero && pip3 install requests
CMD [ "python3", "./led-demo.py" ]
