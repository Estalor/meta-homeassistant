SUMMARY = "Open-source home automation platform running on Python 3"
HOMEPAGE = "https://home-assistant.io/"
SECTION = "devel/python"
LICENSE = "Apache-2.0"
LIC_FILES_CHKSUM = "file://LICENSE.md;md5=86d3f3a95c324c9479bd8986968f4327"

HOMEASSISTANT_CONFIG_DIR ?= "${localstatedir}/lib/homeassistant"
HOMEASSISTANT_CONFIG_DIR[doc] = "Configuration directory used by home-assistant."
HOMEASSISTANT_USER ?= "homeassistant"
HOMEASSISTANT_USER[doc] = "User the home-assistent service runs as."

SRC_URI += "\
    file://homeassistant.service \
    file://0001-Update-pyproject.toml-to-allow-compilation.patch \
"
SRC_URI[sha256sum] = "36f879da8568bdc9a272a62e5c9710bde4d38d377f6478eb293321dabb5029d5"

inherit python_setuptools_build_meta pypi useradd systemd

USERADD_PACKAGES = "${PN}"
GROUPADD_PARAM:${PN} = "homeassistant"
USERADD_PARAM:${PN} = "\
    --system --home ${HOMEASSISTANT_CONFIG_DIR} \
    --no-create-home --shell /bin/false \
    --groups homeassistant,dialout --gid homeassistant ${HOMEASSISTANT_USER} \
"

SYSTEMD_AUTO_ENABLE = "enable"
SYSTEMD_SERVICE:${PN} = "homeassistant.service"

do_install:append () {
    install -d -o ${HOMEASSISTANT_USER} -g homeassistant ${D}${HOMEASSISTANT_CONFIG_DIR}

    # Install systemd unit files and set correct config directory
    install -d ${D}${systemd_unitdir}/system
    install -m 0644 ${WORKDIR}/homeassistant.service ${D}${systemd_unitdir}/system
    sed -i -e 's,@HOMEASSISTANT_CONFIG_DIR@,${HOMEASSISTANT_CONFIG_DIR},g' ${D}${systemd_unitdir}/system/homeassistant.service
    sed -i -e 's,@HOMEASSISTANT_USER@,${HOMEASSISTANT_USER},g' ${D}${systemd_unitdir}/system/homeassistant.service
}

# Home Assistant core

# Components which can be selected and used.
PACKAGES += "\
    ${PN}-amazon-polly \
    ${PN}-assist-pipeline \
    ${PN}-axis \
    ${PN}-backup \
    ${PN}-bluetooth \
    ${PN}-cast \
    ${PN}-cloud \
    ${PN}-conversation \
    ${PN}-dhcp \
    ${PN}-file-upload \
    ${PN}-fritz \
    ${PN}-fritzbox \
    ${PN}-frontend \
    ${PN}-google-translate \
    ${PN}-hardware \
    ${PN}-hacs \
    ${PN}-http \
    ${PN}-image-upload \
    ${PN}-ipp \
    ${PN}-keyboard-remote \
    ${PN}-met \
    ${PN}-mobile-app \
    ${PN}-modbus \
    ${PN}-octoprint \
    ${PN}-pulseaudio-loopback \
    ${PN}-radio-browser \
    ${PN}-recorder \
    ${PN}-route53 \
    ${PN}-sentry \
    ${PN}-shelly \
    ${PN}-ssdp \
    ${PN}-stream \
    ${PN}-systemmonitor \
    ${PN}-tts \
    ${PN}-upnp \
    ${PN}-usb \
    ${PN}-zeroconf \
"

ALLOW_EMPTY:${PN}-amazon-polly = "1"
RDEPENDS:${PN}-amazon-polly = "\
    ${PYTHON_PN}-boto3 (>=1.20.24) \
"

ALLOW_EMPTY:${PN}-assist-pipeline = "1"
RDEPENDS:${PN}-assist-pipeline = "\
    ${PYTHON_PN}-webrtcvad (=2.0.10) \
"

ALLOW_EMPTY:${PN}-axis = "1"
RDEPENDS:${PN}-axis = "\
    ${PYTHON_PN}-axis (=48) \
"

ALLOW_EMPTY:${PN}-backup = "1"
RDEPENDS:${PN}-backup = "\
    ${PYTHON_PN}-securetar (=2023.3.0) \
"

ALLOW_EMPTY:${PN}-bluetooth = "1"
RDEPENDS:${PN}-bluetooth = "\
    ${PYTHON_PN}-bleak (=0.20.2) \
    ${PYTHON_PN}-bleak-retry-connector (=3.1.1) \
    ${PYTHON_PN}-bluetooth-adapters (=0.16.0) \
    ${PYTHON_PN}-bluetooth-auto-recovery (=1.2.1) \
    ${PYTHON_PN}-bluetooth-data-tools (=1.6.1) \
    ${PYTHON_PN}-dbus-fast (=1.87.5) \
"

ALLOW_EMPTY:${PN}-cast = "1"
RDEPENDS:${PN}-cast = "\
    ${PYTHON_PN}-pychromecast (=13.0.7) \
"

ALLOW_EMPTY:${PN}-cloud = "1"
RDEPENDS:${PN}-cloud = "\
    ${PYTHON_PN}-hass-nabucasa (=0.69.0) \
"

ALLOW_EMPTY:${PN}-conversation = "1"
RDEPENDS:${PN}-conversation = "\
    ${PYTHON_PN}-hassil (=1.2.5) \
    ${PYTHON_PN}-home-assistant-intents (=2023.7.25) \
"

ALLOW_EMPTY:${PN}-dhcp = "1"
RDEPENDS:${PN}-dhcp = "\
    ${PYTHON_PN}-aiodiscover (=1.4.16) \
    ${PYTHON_PN}-scapy (=2.5.0) \
"

ALLOW_EMPTY:${PN}-file-upload = "1"
RDEPENDS:${PN}-file-upload = "\
    ${PYTHON_PN}-janus (=1.0.0) \
"

ALLOW_EMPTY:${PN}-fritz = "1"
RDEPENDS:${PN}-fritz = "\
    ${PYTHON_PN}-fritzconnection (=1.12.0) \
    ${PYTHON_PN}-xmltodict (=0.13.0) \
"

ALLOW_EMPTY:${PN}-fritzbox = "1"
RDEPENDS:${PN}-fritzbox = "\
    ${PYTHON_PN}-pyfritzhome (=0.6.8) \
"

ALLOW_EMPTY:${PN}-frontend = "1"
RDEPENDS:${PN}-frontend = "\
    ${PYTHON_PN}-home-assistant-frontend (=20230802.0) \
"

ALLOW_EMPTY:${PN}-google-translate = "1"
RDEPENDS:${PN}-google-translate = "\
    ${PYTHON_PN}-gtts (=2.2.4) \
"

ALLOW_EMPTY:${PN}-hardware = "1"
RDEPENDS:${PN}-hardware = "\
    ${PYTHON_PN}-psutil-home-assistant (=0.0.1) \
"

ALLOW_EMPTY:${PN}-hacs = "1"
RDEPENDS:${PN}-hacs = "\
    ${PYTHON_PN}-aiogithubapi (=22.10.1) \
"

ALLOW_EMPTY:${PN}-http = "1"
RDEPENDS:${PN}-http = "\
    ${PYTHON_PN}-aiohttp-cors (=0.7.0) \
"

ALLOW_EMPTY:${PN}-image-upload = "1"
RDEPENDS:${PN}-image-upload = "\
    ${PYTHON_PN}-pillow (>=9.5.0) \
"

ALLOW_EMPTY:${PN}-ipp = "1"
RDEPENDS:${PN}-ipp = "\
    ${PYTHON_PN}-pyipp (=0.14.2) \
"

ALLOW_EMPTY:${PN}-keyboard-remote = "1"
RDEPENDS:${PN}-keyboard-remote = "\
    ${PYTHON_PN}-evdev (>=1.6.1) \
    ${PYTHON_PN}-asyncinotify (>=4.0.2) \
"

ALLOW_EMPTY:${PN}-met = "1"
RDEPENDS:${PN}-met = "\
    ${PYTHON_PN}-pymetno (>=0.9.0) \
"

ALLOW_EMPTY:${PN}-mobile-app = "1"
RDEPENDS:${PN}-mobile-app = "\
    ${PYTHON_PN}-pynacl (=1.5.0) \
"

ALLOW_EMPTY:${PN}-modbus = "1"
RDEPENDS:${PN}-modbus = "\
    ${PYTHON_PN}-pymodbus (>=3.3.1) \
"

ALLOW_EMPTY:${PN}-octoprint = "1"
RDEPENDS:${PN}-octoprint = "\
    ${PYTHON_PN}-pyoctoprintapi (=0.1.11) \
"

ALLOW_EMPTY:${PN}-pulseaudio-loopback = "1"
RDEPENDS:${PN}-pulseaudio-loopback = "\
    ${PYTHON_PN}-pulsectl (>=23.5.2) \
"

ALLOW_EMPTY:${PN}-radio-browser = "1"
RDEPENDS:${PN}-radio-browser = "\
    ${PYTHON_PN}-radios (=0.1.1) \
"

ALLOW_EMPTY:${PN}-recorder = "1"
RDEPENDS:${PN}-recorder = "\
    ${PYTHON_PN}-fnv-hash-fast (=0.4.0) \
    ${PYTHON_PN}-sqlalchemy (>=2.0.15) \
"

ALLOW_EMPTY:${PN}-route53 = "1"
RDEPENDS:${PN}-route53 = "\
    ${PYTHON_PN}-boto3 (>=1.20.24) \
"

ALLOW_EMPTY:${PN}-sentry = "1"
RDEPENDS:${PN}-sentry = "\
    ${PYTHON_PN}-sentry-sdk (>=1.28.1) \
"

ALLOW_EMPTY:${PN}-shelly = "1"
RDEPENDS:${PN}-shelly = "\
    ${PYTHON_PN}-aioshelly (=5.4.0) \
"

ALLOW_EMPTY:${PN}-ssdp = "1"
RDEPENDS:${PN}-ssdp = "\
    ${PYTHON_PN}-async-upnp-client (=0.34.1) \
"

ALLOW_EMPTY:${PN}-stream = "1"
RDEPENDS:${PN}-stream = "\
    ${PYTHON_PN}-pyturbojpeg (=1.7.1) \
    ${PYTHON_PN}-ha-av (=10.1.1) \
    ${PYTHON_PN}-numpy (>=1.23.2) \
"

ALLOW_EMPTY:${PN}-systemmonitor = "1"
RDEPENDS:${PN}-systemmonitor = "\
    ${PYTHON_PN}-psutil (>=5.9.5) \
"

ALLOW_EMPTY:${PN}-tts = "1"
RDEPENDS:${PN}-tts = "\
    ${PYTHON_PN}-mutagen (=1.46.0) \
"

ALLOW_EMPTY:${PN}-upnp = "1"
RDEPENDS:${PN}-upnp = "\
    ${PYTHON_PN}-async-upnp-client (=0.34.1) \
    ${PYTHON_PN}-getmac (=0.8.2) \
"

ALLOW_EMPTY:${PN}-usb = "1"
RDEPENDS:${PN}-usb = "\
    ${PYTHON_PN}-pyserial (=3.5) \
    ${PYTHON_PN}-pyudev (>=0.23.2) \
"

ALLOW_EMPTY:${PN}-zeroconf = "1"
RDEPENDS:${PN}-zeroconf = "\
    ${PYTHON_PN}-zeroconf (>=0.58.2) \
"

RDEPENDS:${PN} = "\
    ${PYTHON_PN}-aiohttp (=3.8.5) \
    ${PYTHON_PN}-astral (=2.2) \
    ${PYTHON_PN}-async-timeout (=4.0.2) \
    ${PYTHON_PN}-attrs (>=22.2.0) \
    ${PYTHON_PN}-atomicwrites-homeassistant (=1.4.1) \
    ${PYTHON_PN}-awesomeversion (=22.9.0) \
    ${PYTHON_PN}-bcrypt (=4.0.1) \   
    ${PYTHON_PN}-certifi (>=2021.5.30) \
    ${PYTHON_PN}-ciso8601 (=2.3.0) \
    ${PYTHON_PN}-httpx (=0.24.1) \
    ${PYTHON_PN}-home-assistant-bluetooth (=1.10.2) \
    ${PYTHON_PN}-ifaddr (>=0.2.0) \
    ${PYTHON_PN}-jinja2 (=3.1.2) \
    ${PYTHON_PN}-lru-dict (=1.2.0) \
    ${PYTHON_PN}-pyjwt (>=2.8.0) \
    ${PYTHON_PN}-cryptography (>=41.0.2) \
    ${PYTHON_PN}-pyopenssl (>=23.2.0) \
    ${PYTHON_PN}-orjson (=3.9.2) \
    ${PYTHON_PN}-pip (>=21.3.1) \
    ${PYTHON_PN}-python-slugify (=4.0.1) \
    ${PYTHON_PN}-pyyaml (= 6.0.1) \
    ${PYTHON_PN}-requests (=2.31.0) \
    ${PYTHON_PN}-typing-extensions (>=4.7.0) \
    ${PYTHON_PN}-ulid-transform (=0.8.0) \
    ${PYTHON_PN}-voluptuous (=0.13.1) \
    ${PYTHON_PN}-voluptuous-serialize (=2.6.0) \
    ${PYTHON_PN}-yarl (=1.9.2) \
    \
    ${PYTHON_PN}-statistics \
    ${PYTHON_PN}-sqlite3 \
    \
    ${PN}-assist-pipeline \
    ${PN}-bluetooth \
    ${PN}-cloud \
    ${PN}-conversation \
    ${PN}-dhcp \
    ${PN}-file-upload \
    ${PN}-frontend \
    ${PN}-hardware \
    ${PN}-google-translate \
    ${PN}-http \
    ${PN}-image-upload \
    ${PN}-mobile-app \
    ${PN}-recorder \
    ${PN}-stream \
    ${PN}-usb \
    ${PN}-zeroconf \
"

RRECOMMENDS:${PN} = "\
    ${PN}-amazon-polly \
    ${PN}-axis \
    ${PN}-backup \
    ${PN}-cast \
    ${PN}-fritz \
    ${PN}-fritzbox \
    ${PN}-ipp \
    ${PN}-keyboard-remote \
    ${PN}-met \
    ${PN}-modbus \
    ${PN}-octoprint \
    ${PN}-pulseaudio-loopback \
    ${PN}-radio-browser \
    ${PN}-route53 \
    ${PN}-sentry \
    ${PN}-shelly \
    ${PN}-systemmonitor \
    ${PN}-tts \
    ${PN}-upnp \
    ${PN}-ssdp \
    ${PN}-hacs \
"
