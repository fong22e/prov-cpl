cd /home/cpl/prov-cpl

# uninstall CPL
sudo make uninstall

# clean CPL
sudo make clean
sudo make distclean
sudo make messclean

# install CPL
sudo make release
sudo make install

# install r bindings
sh bindings/r/install-bindings-r.sh