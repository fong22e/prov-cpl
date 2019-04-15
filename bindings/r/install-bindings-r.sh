echo "Compilng Attributes..."
cd /home/cpl/prov-cpl/bindings/r/CPL
sudo R -e 'Rcpp::compileAttributes()'

echo "Building CPL..."
cd ..
sudo R CMD build CPL

echo "Installing CPL..."
sudo R CMD install CPL_3.0.tar.gz $R_LIBS_USER
cd $R_LIBS_USER
sudo R -e 'install.packages("CPL_3.0.tar.gz")'

echo "Removing tar ball..."
cd /home/cpl/prov-cpl/bindings/r
sudo rm CPL_3.0.tar.gz

cd /home/cpl/prov-cpl