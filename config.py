"""
    Manage environment variables
    
    Created by: Edward Cacho Casas
"""
import os

# acs prop

AZURE_DEV_ACS_BASE_URL = os.environ.get("AZURE_DEV_ACS_BASE_URL")
AZURE_DEV_ACS_TOKEN = os.environ.get("AZURE_DEV_ACS_TOKEN")


AZURE_CER_ACS_BASE_URL = os.environ.get("AZURE_CER_ACS_BASE_URL")
AZURE_CER_ACS_TOKEN = os.environ.get("AZURE_CER_ACS_TOKEN")

# aks prop atla

AZURE_DEV_BASE_URL = os.environ.get("AZURE_DEV_BASE_URL")
AZURE_DEV_TOKEN = os.environ.get("AZURE_DEV_TOKEN")

AZURE_CER_BASE_URL = os.environ.get("AZURE_CER_BASE_URL")
AZURE_CER_TOKEN = os.environ.get("AZURE_CER_TOKEN")


# aks other suscription

# lego

AZURE_DEV_LEGO_BASE_URL = os.environ.get("AZURE_DEV_LEGO_BASE_URL")
AZURE_DEV_LEGO_TOKEN = os.environ.get("AZURE_DEV_LEGO_TOKEN")

# trhi

AZURE_DEV_TRHI_BASE_URL = os.environ.get("AZURE_DEV_TRHI_BASE_URL")
AZURE_DEV_TRHI_TOKEN = os.environ.get("AZURE_DEV_TRHI_TOKEN")

# ppel

AZURE_DEV_PPEL_BASE_URL = os.environ.get("AZURE_DEV_PPEL_BASE_URL")
AZURE_DEV_PPEL_TOKEN = os.environ.get("AZURE_DEV_PPEL_TOKEN")

# eva0

AZURE_DEV_EVA0_BASE_URL = os.environ.get("AZURE_DEV_EVA0_BASE_URL")
AZURE_DEV_EVA0_TOKEN = os.environ.get("AZURE_DEV_EVA0_TOKEN")

# ntlc

AZURE_DEV_NTLC_BASE_URL = os.environ.get("AZURE_DEV_NTLC_BASE_URL")
AZURE_DEV_NTLC_TOKEN = os.environ.get("AZURE_DEV_NTLC_TOKEN")

# wise

AZURE_DEV_WISE_BASE_URL = os.environ.get("AZURE_DEV_WISE_BASE_URL")
AZURE_DEV_WISE_TOKEN = os.environ.get("AZURE_DEV_WISE_TOKEN")

# fnel

AZURE_DEV_FNEL_BASE_URL = os.environ.get("AZURE_DEV_FNEL_BASE_URL")
AZURE_DEV_FNEL_TOKEN = os.environ.get("AZURE_DEV_FNEL_TOKEN")


# ocp prop

OC_DEV_BASE_URL = os.environ.get("OC_DEV_BASE_URL")
OC_DEV_TOKEN = os.environ.get("OC_DEV_TOKEN")

PMBRK_DEV_BASE_URL = os.environ.get("PMBRK_DEV_BASE_URL")
PMBRK_DEV_TOKEN = os.environ.get("PMBRK_DEV_TOKEN")

OC_CER_CHO_BASE_URL = os.environ.get("OC_CER_CHO_BASE_URL")
OC_CER_CHO_TOKEN = os.environ.get("OC_CER_CHO_TOKEN")
OC_CER_MOL_BASE_URL = os.environ.get("OC_CER_MOL_BASE_URL")
OC_CER_MOL_TOKEN = os.environ.get("OC_CER_MOL_TOKEN")

APP_PORT = int(os.environ.get("APP_PORT", 8080))
DEBUG = bool(os.environ.get("DEBUG", True))