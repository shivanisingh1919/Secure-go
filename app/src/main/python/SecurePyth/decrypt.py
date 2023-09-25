import cv2
import numpy 
import sys
import time
import math


def decrypt(source,key):
    #get value
    recieved=decode(source)
    if(recieved=="error"):
        return "error"
    
    file1=Dat2IMG(recieved,key,source)
    
    return file1



def decode(image_name):
    #print("[+] Decoding...")
    # read the image
    image = cv2.imread(image_name)
    decoded_data = ""
    binary_data = ""
    cont=True
    for row in image:
        for pixel in row:
            r, g, b = to_bin(pixel)
            binary_data += r[-1]
            binary_data += g[-1]
            binary_data += b[-1]
        # split by 8-bits
        all_bytes = [ binary_data[i: i+8] for i in range(0, len(binary_data), 8) ]
        # convert from bits to characters
        for byte in all_bytes:
            decoded_data += chr(int(byte, 2))
            if decoded_data[-5:] == "=====":
                cont=False
                return decoded_data[:-5]
        binary_data=""
        if(cont==False):
            break
    if decoded_data[-5:] == "=====":
        return decoded_data[:-5]
    else:
        return "error" 

def to_bin(data):
    if isinstance(data, str):
        return ''.join([ format(ord(i), "08b") for i in data ])
    elif isinstance(data, bytes) or isinstance(data, numpy.ndarray):
        return [ format(i, "08b") for i in data ]
    elif isinstance(data, int) or isinstance(data, numpy.uint8):
        return format(data, "08b")
    else:
        return "error"


def Dat2IMG(strdat,key,source):
    if(key!=strdat[:len(key)]):
        return  "error1"
    strdat=strdat[len(key):]
    if strdat=="":
        return "error2"
    contour=[]
    coords_arr=[]
    coord_xy=[]
    value=""
    maxx=0
    maxy=0
    for c in strdat:
        if(c==","):
            #save coords in coords array
            try:
                coord_xy=coord_xy+[int(value)]
            except ValueError:
                return "error3"
            value=""
            if(len(coord_xy)==2):
                if(coord_xy[0]>maxx):
                    maxx=coord_xy[0] 
                if(coord_xy[1]>maxy):
                    maxy=coord_xy[1]
                if(coord_xy[0]==0 and coord_xy[1]==0):
                    # save coords array in  shape
                    coord_xy=[]
                    contour=contour+[numpy.array(coords_arr)]
                    coords_arr=[]
                else:
                    coords_arr=coords_arr+[coord_xy]
                    coord_xy=[]
        else:
            value=value+c

    #imgout=cv2.imread("out.jpg")
    imgout=255 * numpy.ones(shape=[maxy+20,maxx+20,3],dtype=numpy.uint8)
    for i in range(1,len(contour)):
        cv2.drawContours(imgout, contour,i , (0, 0, 0), 1)
    file1=getPath(source)+"dec_"+str(math.floor(time.time()))+".png"
    cv2.imwrite(file1, imgout)
    return file1
def getPath(source):
    line=""
    path=""
    for character in source:
        line=line+character
        if(character=="\\" or character=="/"):
            path=path+line
            line=""
    return path



#for testing purpose
#print(decrypt(sys.argv[1],sys.argv[2]))
