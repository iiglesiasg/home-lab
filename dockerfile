FROM arm32v7/python:3.7-buster 
ADD led-demo.py /
RUN pip3 install --no-cache-dir gpiozero  requests logging
CMD [ "python3", "./led-demo.py" ]
