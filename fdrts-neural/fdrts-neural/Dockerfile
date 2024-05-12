FROM python:3.8-slim-buster

WORKDIR /python-docker

COPY requirements.txt requirements.txt

#RUN pip3 install --upgrade setuptools

RUN pip install -r requirements.txt

COPY . .
EXPOSE 8082
CMD [ "python", "main.py"]