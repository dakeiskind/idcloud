define([], function() {
    var data = {
	    "ports": [
	        {
	            "status": "ACTIVE", 
	            "network_id": "e9242f70-eb04-42e4-9007-0f3b5fbfa77b", 
	            "url": "/dashboard/project/networks/ports/02617ede-032b-45d0-a3e5-e1ba3fccf5d5/detail", 
	            "device_owner": "compute:nova", 
	            "fixed_ips": [
	                {
	                    "subnet_id": "79f53b69-6f18-43ad-aed0-08fedbaa77f9", 
	                    "ip_address": "10.10.10.3"
	                }
	            ], 
	            "id": "02617ede-032b-45d0-a3e5-e1ba3fccf5d5", 
	            "device_id": "d7bc9f6d-2440-4961-86c3-7c842a1d3d87"
	        }, 
	        {
	            "status": "ACTIVE", 
	            "network_id": "0654cea0-1c6f-4c6a-b9ec-00a780ea6b64", 
	            "url": "/dashboard/project/networks/ports/40edf9e1-f824-4f74-84bb-764f9a46c936/detail", 
	            "device_owner": "compute:nova", 
	            "fixed_ips": [
	                {
	                    "subnet_id": "bcc3defd-e139-4f91-9783-b41c61af512c", 
	                    "ip_address": "192.168.1.3"
	                }, 
	                {
	                    "subnet_id": "1cdc6eb1-7184-43b0-94b7-f319eb4bfc1d", 
	                    "ip_address": "fdcc:a320:52ce:0:f816:3eff:febf:4b29"
	                }
	            ], 
	            "id": "40edf9e1-f824-4f74-84bb-764f9a46c936", 
	            "device_id": "892b3423-e479-4336-b7c8-15e8ac2ca88f"
	        }, 
	        {
	            "status": "ACTIVE", 
	            "network_id": "0654cea0-1c6f-4c6a-b9ec-00a780ea6b64", 
	            "url": "/dashboard/project/networks/ports/49ba1853-f317-4be7-aaaa-d9b6a2567523/detail", 
	            "device_owner": "compute:nova", 
	            "fixed_ips": [
	                {
	                    "subnet_id": "bcc3defd-e139-4f91-9783-b41c61af512c", 
	                    "ip_address": "192.168.1.4"
	                }, 
	                {
	                    "subnet_id": "1cdc6eb1-7184-43b0-94b7-f319eb4bfc1d", 
	                    "ip_address": "fdcc:a320:52ce:0:f816:3eff:fe52:4174"
	                }
	            ], 
	            "id": "49ba1853-f317-4be7-aaaa-d9b6a2567523", 
	            "device_id": "d7bc9f6d-2440-4961-86c3-7c842a1d3d87"
	        }, 
	        // {
	        //     "status": "ACTIVE", 
	        //     "network_id": "e9242f70-eb04-42e4-9007-0f3b5fbfa77b", 
	        //     "url": "/dashboard/project/networks/ports/5b6e0485-2326-48d1-84a1-ac5dffcbdfa5/detail", 
	        //     "device_owner": "network:dhcp", 
	        //     "fixed_ips": [
	        //         {
	        //             "subnet_id": "79f53b69-6f18-43ad-aed0-08fedbaa77f9", 
	        //             "ip_address": "10.10.10.2"
	        //         }
	        //     ], 
	        //     "id": "5b6e0485-2326-48d1-84a1-ac5dffcbdfa5", 
	        //     "device_id": "dhcpd2d4195f-a3c4-5794-b780-43bceacba2f4-e9242f70-eb04-42e4-9007-0f3b5fbfa77b"
	        // }, 
	        {
	            "status": "ACTIVE", 
	            "network_id": "e9242f70-eb04-42e4-9007-0f3b5fbfa77b", 
	            "url": "/dashboard/project/networks/ports/7f6e499b-9048-481c-94c4-1b382c2f96c4/detail", 
	            "device_owner": "network:router_interface", 
	            "fixed_ips": [
	                {
	                    "subnet_id": "79f53b69-6f18-43ad-aed0-08fedbaa77f9", 
	                    "ip_address": "10.10.10.1"
	                }
	            ], 
	            "id": "7f6e499b-9048-481c-94c4-1b382c2f96c4", 
	            "device_id": "router-test-01"
	        }, 
	        // {
	        //     "status": "ACTIVE", 
	        //     "network_id": "0654cea0-1c6f-4c6a-b9ec-00a780ea6b64", 
	        //     "url": "/dashboard/project/networks/ports/7fe41253-8d2f-410e-bd7f-11f352b1a74e/detail", 
	        //     "device_owner": "network:dhcp", 
	        //     "fixed_ips": [
	        //         {
	        //             "subnet_id": "faaabcc5-4743-4869-9979-77323548df71", 
	        //             "ip_address": "10.0.0.2"
	        //         }, 
	        //         {
	        //             "subnet_id": "bcc3defd-e139-4f91-9783-b41c61af512c", 
	        //             "ip_address": "192.168.1.2"
	        //         }, 
	        //         {
	        //             "subnet_id": "1cdc6eb1-7184-43b0-94b7-f319eb4bfc1d", 
	        //             "ip_address": "fdcc:a320:52ce:0:f816:3eff:fe08:ab4a"
	        //         }
	        //     ], 
	        //     "id": "7fe41253-8d2f-410e-bd7f-11f352b1a74e", 
	        //     "device_id": "dhcpd2d4195f-a3c4-5794-b780-43bceacba2f4-0654cea0-1c6f-4c6a-b9ec-00a780ea6b64"
	        // }, 
	        // {
	        //     "status": "ACTIVE", 
	        //     "network_id": "0654cea0-1c6f-4c6a-b9ec-00a780ea6b64", 
	        //     "url": "/dashboard/project/networks/ports/ce30f81e-c209-41e8-8c0b-4d039a62a410/detail", 
	        //     "device_owner": "network:router_interface", 
	        //     "fixed_ips": [
	        //         {
	        //             "subnet_id": "1cdc6eb1-7184-43b0-94b7-f319eb4bfc1d", 
	        //             "ip_address": "fdcc:a320:52ce::1"
	        //         }
	        //     ], 
	        //     "id": "ce30f81e-c209-41e8-8c0b-4d039a62a410", 
	        //     "device_id": "router-test-01"
	        // }, 
	        {
	            "status": "ACTIVE", 
	            "network_id": "0654cea0-1c6f-4c6a-b9ec-00a780ea6b64", 
	            "url": "/dashboard/project/networks/ports/db406dd5-e632-4fb8-be89-fd8f06480bd0/detail", 
	            "device_owner": "network:router_interface", 
	            "fixed_ips": [
	                {
	                    "subnet_id": "faaabcc5-4743-4869-9979-77323548df71", 
	                    "ip_address": "10.0.0.1"
	                }
	            ], 
	            "id": "db406dd5-e632-4fb8-be89-fd8f06480bd0", 
	            "device_id": "router-test-01"
	        }, 
	        {
	            "status": "ACTIVE", 
	            "network_id": "00d8f8a0-4093-47d4-87fb-21e52df72bf0", 
	            "url": "/dashboard/project/networks/ports/f181570c-d055-4c63-abea-def3c1dc23be/detail", 
	            "device_owner": "network:router_gateway", 
	            "fixed_ips": [
	                {
	                    "subnet_id": "5e4a9754-c074-41c6-bd7b-5a87d798c551", 
	                    "ip_address": "192.168.38.240"
	                }, 
	                {
	                    "subnet_id": "496487da-91c3-431a-beba-3f152b4ffecf", 
	                    "ip_address": "2001:db8::1"
	                }
	            ], 
	            "id": "f181570c-d055-4c63-abea-def3c1dc23be", 
	            "device_id": "router-test-01"
	        }
	    ], 
	    "routers": [
	        {
	            "status": "ACTIVE", 
	            "external_gateway_info": {
	                "network_id": "00d8f8a0-4093-47d4-87fb-21e52df72bf0", 
	                "enable_snat": true, 
	                "external_fixed_ips": [
	                    {
	                        "subnet_id": "5e4a9754-c074-41c6-bd7b-5a87d798c551", 
	                        "ip_address": "192.168.38.240"
	                    }, 
	                    {
	                        "subnet_id": "496487da-91c3-431a-beba-3f152b4ffecf", 
	                        "ip_address": "2001:db8::1"
	                    }
	                ]
	            }, 
	            "url": "/dashboard/project/routers/router-test-01/", 
	            "id": "router-test-01", 
	            "name": "router1"
	        }
	    ], 
	    "networks": [
	        {
	            "status": "ACTIVE", 
	            "subnets": [ ], 
	            "name": "public", 
	            "router:external": true, 
	            "url": "/dashboard/project/networks/00d8f8a0-4093-47d4-87fb-21e52df72bf0/detail", 
	            "id": "00d8f8a0-4093-47d4-87fb-21e52df72bf0"
	        }, 
	        {
	            "status": "ACTIVE", 
	            "subnets": [
	                {
	                    "url": "/dashboard/project/networks/subnets/1cdc6eb1-7184-43b0-94b7-f319eb4bfc1d/detail", 
	                    "cidr": "fdcc:a320:52ce::/64", 
	                    "id": "1cdc6eb1-7184-43b0-94b7-f319eb4bfc1d"
	                }, 
	                {
	                    "url": "/dashboard/project/networks/subnets/bcc3defd-e139-4f91-9783-b41c61af512c/detail", 
	                    "cidr": "192.168.1.0/24", 
	                    "id": "bcc3defd-e139-4f91-9783-b41c61af512c"
	                }, 
	                {
	                    "url": "/dashboard/project/networks/subnets/faaabcc5-4743-4869-9979-77323548df71/detail", 
	                    "cidr": "10.0.0.0/24", 
	                    "id": "faaabcc5-4743-4869-9979-77323548df71"
	                }
	            ], 
	            "name": "vpc-network-02", 
	            "router:external": false, 
	            "url": "/dashboard/project/networks/0654cea0-1c6f-4c6a-b9ec-00a780ea6b64/detail", 
	            "id": "0654cea0-1c6f-4c6a-b9ec-00a780ea6b64"
	        }, 
	        {
	            "status": "ACTIVE", 
	            "subnets": [
	                {
	                    "url": "/dashboard/project/networks/subnets/79f53b69-6f18-43ad-aed0-08fedbaa77f9/detail", 
	                    "cidr": "10.10.10.0/24", 
	                    "id": "79f53b69-6f18-43ad-aed0-08fedbaa77f9"
	                }
	            ], 
	            "name": "vpc-network-01", 
	            "router:external": false, 
	            "url": "/dashboard/project/networks/e9242f70-eb04-42e4-9007-0f3b5fbfa77b/detail", 
	            "id": "e9242f70-eb04-42e4-9007-0f3b5fbfa77b"
	        }
	    ], 
	    "servers": [
	        {
	            "status": "ACTIVE", 
	            "task": null, 
	            "console": "vnc", 
	            "name": "vm-test2", 
	            "url": "/dashboard/project/instances/d7bc9f6d-2440-4961-86c3-7c842a1d3d87/", 
	            "id": "d7bc9f6d-2440-4961-86c3-7c842a1d3d87"
	        }, 
	        {
	            "status": "ACTIVE", 
	            "task": null, 
	            "console": "vnc", 
	            "name": "vm-test1", 
	            "url": "/dashboard/project/instances/892b3423-e479-4336-b7c8-15e8ac2ca88f/", 
	            "id": "892b3423-e479-4336-b7c8-15e8ac2ca88f"
	        }
	    ]
	}; 
    return data;
});

