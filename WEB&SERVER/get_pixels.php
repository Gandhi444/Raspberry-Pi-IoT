<?php
$command = escapeshellcmd('sudo python3 get_pixels.py');
$Zad1 = shell_exec($command);
echo $Zad1;