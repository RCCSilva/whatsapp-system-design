FROM python:3.8

WORKDIR /app

RUN apt-get update && apt-get -y install gcc libpq-dev

COPY requirements.txt requirements.txt
RUN pip install -r requirements.txt

COPY templates templates
COPY frontend.py ./

EXPOSE 5000

CMD gunicorn -b :5000 frontend:app