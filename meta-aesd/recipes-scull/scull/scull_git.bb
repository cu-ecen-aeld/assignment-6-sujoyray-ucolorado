# Recipe created by recipetool
# This is the basis of a recipe and may need further editing in order to be fully functional.
# (Feel free to remove these comments when editing.)

# WARNING: the following LICENSE and LIC_FILES_CHKSUM values are best guesses - it is
# your responsibility to verify that the values are complete and correct.
#
# The following license files were not able to be identified and are
# represented as "Unknown" below, you will need to check them yourself:
#   LICENSE
#  CREDIT: Consulted stackoverflow to deal with some of the build issues.
LICENSE = "MIT"
LIC_FILES_CHKSUM = "file://LICENSE;md5=f098732a73b5f6f3430472f5b094ffdb"
INITSCRIPT_PACKAGES="${PN}"
INITSCRIPT_NAME:${PN}="scull_load_unload"
inherit update-rc.d


SRC_URI = "git://git@github.com/cu-ecen-aeld/assignment-7-sujoyray-ucolorado.git;protocol=ssh;branch=master \
           file://0001-Updating-the-makefile-to-build-scull.patch \
           file://scull_load_unload \
           "

# Modify these as desired
PV = "1.0+git${SRCPV}"
SRCREV = "ccd10e97899ba2117aa3c95fa823b328fce9477d"

S = "${WORKDIR}/git"

inherit module

FILES:${PN} += "/etc"
FILES:${PN} += "/etc/init.d"
FILES:${PN} += "/etc/init.d/scull_load_unload"

#EXTRA_OEMAKE:append:task-install = " -C ${STAGING_KERNEL_DIR} M=${S}/scull"

do_install() {
    install -d ${D}/lib/modules/${KERNEL_VERSION}/extra/
    install -m 0644 ${WORKDIR}/git/scull/*.ko ${D}/lib/modules/${KERNEL_VERSION}/extra/
    install -d ${D}${sysconfdir}/init.d
    install -m 0755 ${WORKDIR}/scull_load_unload ${D}/${sysconfdir}/init.d/
}



EXTRA_OEMAKE += "KERNELDIR=${STAGING_KERNEL_DIR}"
