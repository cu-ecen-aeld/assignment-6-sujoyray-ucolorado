# Recipe created by recipetool
# This is the basis of a recipe and may need further editing in order to be fully functional.
# (Feel free to remove these comments when editing.)

# WARNING: the following LICENSE and LIC_FILES_CHKSUM values are best guesses - it is
# your responsibility to verify that the values are complete and correct.
#
# The following license files were not able to be identified and are
# represented as "Unknown" below, you will need to check them yourself:
#   LICENSE
# CREDIT: Consulted stackoverflow to deal with some of the build issues.

LICENSE = "MIT"
LIC_FILES_CHKSUM = "file://LICENSE;md5=f098732a73b5f6f3430472f5b094ffdb"

SRC_URI = "git://git@github.com/cu-ecen-aeld/assignment-7-sujoyray-ucolorado.git;protocol=ssh;branch=master \
           file://0001-Adding-misc-module-only-rest-of-the-modules-are-remo.patch \
           file://misc_module_load_unload \
           "

# Modify these as desired
PV = "1.0+git${SRCPV}"
SRCREV = "ccd10e97899ba2117aa3c95fa823b328fce9477d"
INITSCRIPT_PACKAGES="${PN}"
INITSCRIPT_NAME:${PN}="misc_module_load_unload"
inherit update-rc.d

MODULES_MODULE_SYMVERS_LOCATION = "/usr/src/kernel"



S = "${WORKDIR}/git"

inherit module


FILES:${PN} += "/etc"
FILES:${PN} += "/etc/init.d"
FILES:${PN} += "/etc/init.d/misc_module_load_unload"

#EXTRA_OEMAKE:append:task-install = " -C ${S/TAGING_KERNEL_DIR} M=${S}/misc-modules"

do_install() {
    install -d ${D}/lib/modules/${KERNEL_VERSION}/extra/misc-modules/
    install -m 0644 ${WORKDIR}/git/misc-modules/*.ko ${D}/lib/modules/${KERNEL_VERSION}/extra/misc-modules/
    install -d ${D}${sysconfdir}/init.d
    install -m 0755 ${WORKDIR}/misc_module_load_unload ${D}/${sysconfdir}/init.d/
}

EXTRA_OEMAKE += "KERNELDIR=${STAGING_KERNEL_DIR}"


