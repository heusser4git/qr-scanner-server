# Base image. 1st stage. node:14. Fixed at specific commit hash. Hashes can not be overriden (in contrast to a tag).
FROM node:16-alpine as build

# Set directory in container. All following commands run relative to /usr/src
WORKDIR /usr/src

# Copy package files into container
COPY package*.json ./
# Copy file with configuration and secrets.

# Install production dependencies only.
RUN npm install

# Copy all (not ignored by .dockerignore) files in Dockerfile directory to current workdir (/usr/src) in container
# chown (Change Owner) sets the owner and group of each copied file to node (user) and node (group).
COPY --chown=node:node . .

#RUN npm run dev
# Build the client application
RUN npm run build

# Base image. 2nd stage. node:lts-alpine. Much smaller final image size.
FROM node:16-alpine
MAINTAINER Urs Heusser <info@tcode.ch>

# run application as user node
USER node
WORKDIR /usr/src


# copy files from build state to current workdir
COPY --chown=node:node --from=build /usr/src/node_modules node_modules
COPY --chown=node:node --from=build /usr/src/ .
#COPY --chown=node:node --from=build /usr/src/package*.json .

# Make port accessible from outside the container
EXPOSE 5000

# Start process in container: static http web server serving dist folder on port 1234
CMD ["npm", "start"]



# docker build -t qrscanner-client --quiet .
# docker run -d --name qrscanner-client -p 5000:5000 qrscanner-client

# docker tag qrscanner-client:latest tcodemalans/qrscanner-client:latest
# docker login --username tcodemalans --password-stdin
# docker push tcodemalans/qrscanner-client:latest