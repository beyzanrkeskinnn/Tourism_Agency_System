PGDMP  &                    |            tourismAgency    15.6    16.2 '    *           0    0    ENCODING    ENCODING        SET client_encoding = 'UTF8';
                      false            +           0    0 
   STDSTRINGS 
   STDSTRINGS     (   SET standard_conforming_strings = 'on';
                      false            ,           0    0 
   SEARCHPATH 
   SEARCHPATH     8   SELECT pg_catalog.set_config('search_path', '', false);
                      false            -           1262    16388    tourismAgency    DATABASE     �   CREATE DATABASE "tourismAgency" WITH TEMPLATE = template0 ENCODING = 'UTF8' LOCALE_PROVIDER = libc LOCALE = 'Turkish_T�rkiye.1254';
    DROP DATABASE "tourismAgency";
                postgres    false            �            1259    32777    hotel    TABLE     �  CREATE TABLE public.hotel (
    hotel_id integer NOT NULL,
    hotel_name text NOT NULL,
    hotel_adresses text NOT NULL,
    hotel_mail text NOT NULL,
    hotel_phone text NOT NULL,
    hotel_star text NOT NULL,
    car_park boolean NOT NULL,
    wifi boolean NOT NULL,
    pool boolean NOT NULL,
    fitness boolean NOT NULL,
    concierge boolean NOT NULL,
    spa boolean NOT NULL,
    room_service boolean NOT NULL
);
    DROP TABLE public.hotel;
       public         heap    postgres    false            �            1259    32780    hotel_hotel_id_seq    SEQUENCE     �   ALTER TABLE public.hotel ALTER COLUMN hotel_id ADD GENERATED ALWAYS AS IDENTITY (
    SEQUENCE NAME public.hotel_hotel_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1
);
            public          postgres    false    216            �            1259    32786    pension    TABLE     �   CREATE TABLE public.pension (
    pension_id integer NOT NULL,
    pension_type text NOT NULL,
    hotel_id integer NOT NULL
);
    DROP TABLE public.pension;
       public         heap    postgres    false            �            1259    32789    pension_pension_id_seq    SEQUENCE     �   ALTER TABLE public.pension ALTER COLUMN pension_id ADD GENERATED ALWAYS AS IDENTITY (
    SEQUENCE NAME public.pension_pension_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1
);
            public          postgres    false    218            �            1259    40969    reservation    TABLE     �  CREATE TABLE public.reservation (
    reservation_id integer NOT NULL,
    room_id integer,
    check_in_date date,
    check_out_date date NOT NULL,
    total_price double precision NOT NULL,
    guest_count integer NOT NULL,
    guest_name text NOT NULL,
    guess_citizen_id text NOT NULL,
    guess_phone text NOT NULL,
    guess_mail text NOT NULL,
    adult_count integer NOT NULL,
    child_count integer NOT NULL
);
    DROP TABLE public.reservation;
       public         heap    postgres    false            �            1259    40972    reservation_reservation_id_seq    SEQUENCE     �   ALTER TABLE public.reservation ALTER COLUMN reservation_id ADD GENERATED ALWAYS AS IDENTITY (
    SEQUENCE NAME public.reservation_reservation_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1
);
            public          postgres    false    224            �            1259    32802    room    TABLE     #  CREATE TABLE public.room (
    room_id integer NOT NULL,
    hotel_id integer NOT NULL,
    pension_id integer NOT NULL,
    season_id integer NOT NULL,
    room_type text NOT NULL,
    stock integer NOT NULL,
    adult_price double precision,
    child_price double precision NOT NULL,
    bed_capacity integer NOT NULL,
    square_meter integer NOT NULL,
    television boolean NOT NULL,
    minibar boolean NOT NULL,
    game_console boolean NOT NULL,
    cash_box boolean NOT NULL,
    projection boolean NOT NULL,
    gym boolean NOT NULL
);
    DROP TABLE public.room;
       public         heap    postgres    false            �            1259    32805    room_room_id_seq    SEQUENCE     �   ALTER TABLE public.room ALTER COLUMN room_id ADD GENERATED ALWAYS AS IDENTITY (
    SEQUENCE NAME public.room_room_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1
);
            public          postgres    false    222            �            1259    32795    season    TABLE     �   CREATE TABLE public.season (
    season_id integer NOT NULL,
    hotel_id integer NOT NULL,
    start_date date,
    finish_date date
);
    DROP TABLE public.season;
       public         heap    postgres    false            �            1259    32798    season_season_id_seq    SEQUENCE     �   ALTER TABLE public.season ALTER COLUMN season_id ADD GENERATED ALWAYS AS IDENTITY (
    SEQUENCE NAME public.season_season_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1
);
            public          postgres    false    220            �            1259    16389    user    TABLE     �   CREATE TABLE public."user" (
    user_id integer NOT NULL,
    user_firstname text NOT NULL,
    user_lastname text NOT NULL,
    user_name text NOT NULL,
    user_password text NOT NULL,
    user_role text NOT NULL
);
    DROP TABLE public."user";
       public         heap    postgres    false            �            1259    16392    user_user_id_seq    SEQUENCE     �   ALTER TABLE public."user" ALTER COLUMN user_id ADD GENERATED ALWAYS AS IDENTITY (
    SEQUENCE NAME public.user_user_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1
);
            public          postgres    false    214                      0    32777    hotel 
   TABLE DATA           �   COPY public.hotel (hotel_id, hotel_name, hotel_adresses, hotel_mail, hotel_phone, hotel_star, car_park, wifi, pool, fitness, concierge, spa, room_service) FROM stdin;
    public          postgres    false    216   1                  0    32786    pension 
   TABLE DATA           E   COPY public.pension (pension_id, pension_type, hotel_id) FROM stdin;
    public          postgres    false    218   	2       &          0    40969    reservation 
   TABLE DATA           �   COPY public.reservation (reservation_id, room_id, check_in_date, check_out_date, total_price, guest_count, guest_name, guess_citizen_id, guess_phone, guess_mail, adult_count, child_count) FROM stdin;
    public          postgres    false    224   �2       $          0    32802    room 
   TABLE DATA           �   COPY public.room (room_id, hotel_id, pension_id, season_id, room_type, stock, adult_price, child_price, bed_capacity, square_meter, television, minibar, game_console, cash_box, projection, gym) FROM stdin;
    public          postgres    false    222   Y3       "          0    32795    season 
   TABLE DATA           N   COPY public.season (season_id, hotel_id, start_date, finish_date) FROM stdin;
    public          postgres    false    220   �3                 0    16389    user 
   TABLE DATA           m   COPY public."user" (user_id, user_firstname, user_lastname, user_name, user_password, user_role) FROM stdin;
    public          postgres    false    214   W4       .           0    0    hotel_hotel_id_seq    SEQUENCE SET     A   SELECT pg_catalog.setval('public.hotel_hotel_id_seq', 15, true);
          public          postgres    false    217            /           0    0    pension_pension_id_seq    SEQUENCE SET     E   SELECT pg_catalog.setval('public.pension_pension_id_seq', 24, true);
          public          postgres    false    219            0           0    0    reservation_reservation_id_seq    SEQUENCE SET     M   SELECT pg_catalog.setval('public.reservation_reservation_id_seq', 20, true);
          public          postgres    false    225            1           0    0    room_room_id_seq    SEQUENCE SET     ?   SELECT pg_catalog.setval('public.room_room_id_seq', 20, true);
          public          postgres    false    223            2           0    0    season_season_id_seq    SEQUENCE SET     C   SELECT pg_catalog.setval('public.season_season_id_seq', 14, true);
          public          postgres    false    221            3           0    0    user_user_id_seq    SEQUENCE SET     >   SELECT pg_catalog.setval('public.user_user_id_seq', 5, true);
          public          postgres    false    215                       2606    40981    hotel hotel_pkey 
   CONSTRAINT     T   ALTER TABLE ONLY public.hotel
    ADD CONSTRAINT hotel_pkey PRIMARY KEY (hotel_id);
 :   ALTER TABLE ONLY public.hotel DROP CONSTRAINT hotel_pkey;
       public            postgres    false    216            �           2606    40979    pension pension_pkey 
   CONSTRAINT     Z   ALTER TABLE ONLY public.pension
    ADD CONSTRAINT pension_pkey PRIMARY KEY (pension_id);
 >   ALTER TABLE ONLY public.pension DROP CONSTRAINT pension_pkey;
       public            postgres    false    218            �           2606    40988    reservation reservation_pkey 
   CONSTRAINT     f   ALTER TABLE ONLY public.reservation
    ADD CONSTRAINT reservation_pkey PRIMARY KEY (reservation_id);
 F   ALTER TABLE ONLY public.reservation DROP CONSTRAINT reservation_pkey;
       public            postgres    false    224            �           2606    40990    room room_pkey 
   CONSTRAINT     Q   ALTER TABLE ONLY public.room
    ADD CONSTRAINT room_pkey PRIMARY KEY (room_id);
 8   ALTER TABLE ONLY public.room DROP CONSTRAINT room_pkey;
       public            postgres    false    222            �           2606    40992    season season_pkey 
   CONSTRAINT     W   ALTER TABLE ONLY public.season
    ADD CONSTRAINT season_pkey PRIMARY KEY (season_id);
 <   ALTER TABLE ONLY public.season DROP CONSTRAINT season_pkey;
       public            postgres    false    220            �           2606    40982    pension fk_hotel_id    FK CONSTRAINT     �   ALTER TABLE ONLY public.pension
    ADD CONSTRAINT fk_hotel_id FOREIGN KEY (hotel_id) REFERENCES public.hotel(hotel_id) ON DELETE CASCADE;
 =   ALTER TABLE ONLY public.pension DROP CONSTRAINT fk_hotel_id;
       public          postgres    false    218    3199    216            �           2606    40993    season fk_hotel_id    FK CONSTRAINT     �   ALTER TABLE ONLY public.season
    ADD CONSTRAINT fk_hotel_id FOREIGN KEY (hotel_id) REFERENCES public.hotel(hotel_id) ON DELETE CASCADE;
 <   ALTER TABLE ONLY public.season DROP CONSTRAINT fk_hotel_id;
       public          postgres    false    3199    220    216            �           2606    40998    room fk_hotel_id    FK CONSTRAINT     �   ALTER TABLE ONLY public.room
    ADD CONSTRAINT fk_hotel_id FOREIGN KEY (hotel_id) REFERENCES public.hotel(hotel_id) ON DELETE CASCADE;
 :   ALTER TABLE ONLY public.room DROP CONSTRAINT fk_hotel_id;
       public          postgres    false    222    3199    216            �           2606    49166    room fk_pension    FK CONSTRAINT     �   ALTER TABLE ONLY public.room
    ADD CONSTRAINT fk_pension FOREIGN KEY (pension_id) REFERENCES public.pension(pension_id) NOT VALID;
 9   ALTER TABLE ONLY public.room DROP CONSTRAINT fk_pension;
       public          postgres    false    3201    222    218            �           2606    49161    reservation fk_room_id    FK CONSTRAINT     �   ALTER TABLE ONLY public.reservation
    ADD CONSTRAINT fk_room_id FOREIGN KEY (room_id) REFERENCES public.room(room_id) NOT VALID;
 @   ALTER TABLE ONLY public.reservation DROP CONSTRAINT fk_room_id;
       public          postgres    false    3205    224    222            �           2606    49171    room fk_season    FK CONSTRAINT     �   ALTER TABLE ONLY public.room
    ADD CONSTRAINT fk_season FOREIGN KEY (season_id) REFERENCES public.season(season_id) NOT VALID;
 8   ALTER TABLE ONLY public.room DROP CONSTRAINT fk_season;
       public          postgres    false    220    222    3203               �   x�UOAj�0<�^�[A�P��$���@ы����@,���B{�M�*;%�Yv������g'ߊ�7��:�	��ĝ�R
%|�r�R�<|fr�a_9����O��Iau86�d�]��E�;����H���Z��4,�_��ڼw2��Z�3�y�Fd5�f�ՔL��Jh��[n��$��g�|��ȇх��h����=ɘ��n���e���+!�/��e�          �   x�34��OIT�N�(K�)9��Ӓ�И�#�H����J�Č�NC.CLe朎9��9
�E���+����($��d��4Xbb�ed��Xtdc�B@b^qfe~XԐ34��(Q]�!���� ���Xr�p�$"k����� r�F�      &   �   x���A
�@�u�.�$��3;����(t�FooZG�B���!�b F�;��7�@��Ч1�dA|Кŗf���Pu��
�$%R��҉���V �[Vz�4B3Sm׻��&fEbQ��é�C{6̬0�{�S/E㶷u�ѧ��yoV)�;Vι��V�      $   z   x�e��	�0����)�@����<E���
�{�Z)ߥ���+0�0c]�X�q��"x�@�4{�	hF4'8H}w��I����鈉��!C�U�t�����q�Qe���s���J���?�      "   d   x�}�A�0��J�����C����f2k����D[��4HEuMM���m��2>�N���֍�@��Y�7"B0��/���4]}��ُ��p�����)�         u   x�3�tJ��J�+-��v�>���3	$�ihdl�阒���e��Z�����Z���Ǚ
��R����Rs��9SR�RsS9��+S8�Uބ34'��|N��{rJRA���$�)����� --�     