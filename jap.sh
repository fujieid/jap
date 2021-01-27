#!/bin/bash

# 参考自 hutool 工具
help(){
  echo "--------------------------------------------------------------------------"
  echo ""
  echo "usage: ./jap.sh [updv]"
  echo ""
  echo "-updv [version num]   Update all jap related versions."
  echo ""
  echo "--------------------------------------------------------------------------"
}

case "$1" in
  'updv')
    docs/bin/updVersion.sh $2
	;;
  *)
    help
esac
