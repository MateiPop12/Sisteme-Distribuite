FROM node:20.10.0
WORKDIR /angular-app
COPY . .
RUN npm install
RUN npm install -g @angular/cli
EXPOSE 4200
CMD ["ng","serve","--host","0.0.0.0"]
