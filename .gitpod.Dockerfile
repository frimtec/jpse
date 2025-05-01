FROM gitpod/workspace-full

RUN wget https://github.com/PowerShell/PowerShell/releases/download/v7.1.0/powershell_7.1.0-1.ubuntu.20.04_amd64.deb && \
    sudo add-apt-repository universe && \
    sudo dpkg --force-all -i powershell_7.1.0-1.ubuntu.20.04_amd64.deb && \
    rm powershell_7.1.0-1.ubuntu.20.04_amd64.deb