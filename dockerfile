FROM arm32v7/python:3.7-buster 
ADD led-demo.py /
RUN pip install --no-cache-dir gpiozero request
CMD [ "python", "./led-demo.py" ]
