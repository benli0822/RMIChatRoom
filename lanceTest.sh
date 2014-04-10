#!/bin/bash
cd `pwd`/target/classes

java rmi.SiteClient << EOF
admin add 1
admin add 2
admin add 3
admin add 4
admin add 5
admin add 6
build 1 2
build 2 3
build 2 4
build 1 5
build 5 6
EOF

java rmi.SiteClient << EOF
login 3
send abc
EOF

java rmi.SiteClient << EOF
login 1
send 123
EOF


