export class Usuario{
    id: string;
    usuariocorreo: string;
    nombre: string;
    apellido: string;
    contrasena: string;
    ultimo_inicio_sesion: Date;
    usuario_expirado: boolean;
    usuario_bloqueado: boolean;
    contrasena_vencida: boolean;
    activo: boolean;
}