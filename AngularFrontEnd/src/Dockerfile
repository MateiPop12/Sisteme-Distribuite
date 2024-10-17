FROM node:20.10.0
WORKDIR /angular-app
COPY ./package.json /angular-app
RUN npm install
RUN npm install -g @angular/cli
COPY . .
EXPOSE 4200
CMD ["ng","serve","--host","0.0.0.0"]
