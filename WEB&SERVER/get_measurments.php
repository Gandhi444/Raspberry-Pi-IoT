<?php
$command = escapeshellcmd('sudo python3 et_measurments.py');
$Zad1 = shell_exec($command);
echo $Zad1;