<?php
$command = $_GET['x'] . ' '. $_GET['y'] . ' '. $_GET['r'] . ' '. $_GET['g'] . ' '. $_GET['b'];
$command = escapeshellcmd('sudo python3 set_pixel.py' . ' ' . $command);
echo shell_exec($command);
?>