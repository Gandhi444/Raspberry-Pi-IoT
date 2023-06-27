<?php
$command = escapeshellcmd('sudo python3 get_measurments.py');
$measurments = shell_exec($command);
$command = escapeshellcmd('sudo python3 get_orientation.py');
$orientation = shell_exec($command);
$command = escapeshellcmd('sudo python3 get_pixels.py');
$pixels = shell_exec($command);
$myfile = fopen("./joystick.dat", "r") or die("Unable to open file!");
$joystick=fread($myfile,filesize("./joystick.dat"));
echo json_encode(
    array_merge(
        json_decode($measurments, true),
        json_decode($orientation, true),
        json_decode($pixels,true),
        json_decode($joystick,true)
    )
    );