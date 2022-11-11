<?php
    $connect=mysqli_connect("localhost","u606784881_hadiana","@Hasan90256801","u606784881_sia");

    $prodi = $_POST["prodi"];
    $semester = $_POST["semester"];
    $sql = "SELECT * FROM krs WHERE prodi = '$prodi' AND semester = '$semester'";
    
    $result = mysqli_query($connect,$sql);
    $data = array();

    while($row = mysqli_fetch_assoc($result)){
        $index['nim'] = $row['nim'];
        $index['nama'] = $row['nama'];
        $index['prodi'] = $row['prodi'];
        $index['semester'] = $row['semester'];
        // $index['matkul1'] = $row['matkul1'];
        // $index['jmlmatkul1'] = $row['jumlah_sks_matkul1'];
        // $index['matkul2'] = $row['matkul2'];
        // $index['jmlmatkul2'] = $row['jumlah_sks_matkul2'];
        // $index['matkul3'] = $row['matkul3'];
        // $index['jmlmatkul3'] = $row['jumlah_sks_matkul3'];
        // $index['matkul4'] = $row['matkul4'];
        // $index['jmlmatkul4'] = $row['jumlah_sks_matkul4'];
        // $index['matkul5'] = $row['matkul5'];
        // $index['jmlmatkul5'] = $row['jumlah_sks_matkul5'];
        // $index['matkul6'] = $row['matkul6'];
        // $index['jmlmatkul6'] = $row['jumlah_sks_matkul6'];
        // $index['matkul7'] = $row['matkul7'];
        // $index['jmlmatkul7'] = $row['jumlah_sks_matkul7'];
        // $index['matkul8'] = $row['matkul8'];
        // $index['jmlmatkul8'] = $row['jumlah_sks_matkul8'];

        // $index['matkulbatal1'] = $row['matkulbatal1'];
        // $index['jmlmatkulbatal1'] = $row['jumlah_batal_matkul1'];
        // $index['matkulbatal2'] = $row['matkulbatal2'];
        // $index['jmlmatkulbatal2'] = $row['jumlah_batal_matkul2'];
        // $index['matkulbatal3'] = $row['matkulbatal3'];
        // $index['jmlmatkulbatal3'] = $row['jumlah_batal_matkul3'];

        // $index['jmlsks'] = $row['jumlah_sks'];


        array_push($data, $index);
    }
    
    echo json_encode($data);

?>