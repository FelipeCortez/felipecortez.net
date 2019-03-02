rm -rf build
mkdir build
cd build

wget 127.0.0.1:3000 -O cv.html

cp -r ../gen/resources/public/* .
weasyprint cv.html cv.pdf
ls | grep -v cv.pdf | xargs rm
