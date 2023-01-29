FROM node:16-alpine as build
WORKDIR /usr/src
COPY package*.json ./
RUN npm install
COPY --chown=node:node . .
RUN npm run build

FROM busybox:1.35
MAINTAINER Urs Heusser <info@tcode.ch>
RUN adduser -D static
USER static
WORKDIR /home/static
COPY --chown=static:static --from=build /usr/src/public/ .
CMD ["busybox", "httpd", "-f", "-v", "-p", "5000"]
EXPOSE 5000